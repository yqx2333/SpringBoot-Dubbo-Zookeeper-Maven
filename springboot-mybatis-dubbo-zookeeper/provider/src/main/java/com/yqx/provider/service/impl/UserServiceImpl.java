package com.yqx.provider.service.impl;

import com.yqx.common.domin.User;
import com.yqx.common.service.UserService;
import com.yqx.provider.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

// 在provider 服务提供者中创建UserServiceImpl接口
// 进行业务逻辑判断
// @Service(version = "1.0.0")       // Dubbo家用Service
// @Component      // Spring家用Component

public class UserServiceImpl implements UserService {

    @Resource
    private RedisTemplate<String,User> redisTemplate;



    @Autowired
    private UserMapper userMapper;

    /**
     * 查询User信息 返回伪数据
     * @return
     */
    @Override
    public User findUser() {
        return new User(1,"于清晰",23,"男");
    }

    /**
     * 查询所有User信息
     * @return
     */
    @Override
    public List<User> findUserAll() {
        return userMapper.findUserAll();
    }

    /**
     * 根据UserID查询单个User信息
     * 主要测试TK包下的Mapper是否能够使用
     * @return
     */
    @Override
    public List<User> findUsers() {
        return userMapper.selectAll();
    }

    /**
     * 根据UserID查询User对象
     * @return
     */
    @Override
    public User findUserById( Integer id ) {
        // 使用selectByPrimaryKey时候 必须在实体类中添加@Id注解 表明主键
        User user = userMapper.selectByPrimaryKey( id );
        // 非空判断
        if ( user == null ){
            throw new RuntimeException();
        }
        // 不为空返回
        return user;
    }




    // ------------------------------------------------------------------
    // ------------------------------------------------------------------
    // --------------------------Redis实现CRUD----------------------------
    // ------------------------------------------------------------------
    // ------------------------------------------------------------------

    // Redis核心:
    // 如果缓存存在 则从Reids中读取信息
    // 如果缓存不存在 则从DB中获取信息 再插入缓存

    /**
     * 测试Redis  根据UserID查询User对象
     * 本地测试成功! Redis缓存成功! 远程调用成功!
     * @param id
     * @return
     */
    @Override
    public User getUserById(Integer id) {

        // 设置Redis事务关闭
        redisTemplate.setEnableTransactionSupport( false );

        // 设置key信息
        String key = "user_"+id;
        ValueOperations<String,User> operations = redisTemplate.opsForValue();

        //缓存存在
        boolean hasKey = redisTemplate.hasKey(key);
        User u = operations.get(key);
        System.out.println("是否有缓存："+hasKey+"  缓存中的值是："+u);
        if(hasKey){
            User user = operations.get(key);
            LOGGER.info("UserImpl.updateUser() : 从缓存中获取了user >> " + user.toString());
            return user;
        }
        //从数据库中获取user数据
        User user = userMapper.getUserById(id);

        //插入缓存
        operations.set(key, user, 4, TimeUnit.HOURS);
        LOGGER.info("UserImpl.findUserById() :user插入缓存 >> " + user.toString());
        return user;
    }

    /**
     * 根据User对象 保存User信息
     * 添加保存不需要进行Redis缓存 直接添加即可
     * @param user
     * @return
     */
    @Override
    public int saveUser(User user) {

        // 先进行判断 如果传入的User对象为空该怎么办?
        if ( user == null ){
            LOGGER.info( "增加User对象时,传入的User对象为空!" + user );
            throw new RuntimeException();
        }
        // 不为空则进行添加
        int i = userMapper.saveUser(user);
        return i;
    }

    /**
     * 根据传入的User对象 修改信息 修改信息之后修改Redis中的内容
     * 如果缓存存在,则从缓存中删除User信息
     * 如果缓存不存在 不做任何操作
     * @param user
     * @return
     */
    @Override
    public int updateUser(User user) {

        // 先判断传入的User对象是否为空
        if ( user == null ){
            // 如果传入的User为空 直接报错
            throw new RuntimeException();
        }

        // 如果不为空 则修改数据
        int update = userMapper.updateUser(user);

        // 设置key值   key值设置为: user_id
        String key = "user_" + user.getId();
        // 判断该key值是否在缓存中存在
        Boolean hasKey = redisTemplate.hasKey(key);

        // 如果存在
        if ( hasKey ){
            // 将该key和对应的value删除
            redisTemplate.delete( key );
            // 记录日志
            LOGGER.info("UserImmpl.updateUser() : 从缓存中删除user >> " + user.toString());
        }
        
        // 如果该key不存在于Redis缓存中 则不做任何操作
        return update;
    }

    /**
     * 根据传入的UserID 删除该User对象
     * @param id
     * @return
     */
    @Override
    public int deleteUser(Integer id) {

        // 判断传入的id是否为空
        if ( id == 0 || id == null ){
            // 如果传入的参数为空 则报错
            throw new RuntimeException();
        }

        // 传入正确参数时 进行删除操作
        int delete = userMapper.deleteUser(id);

        // 获取key对象
        String key = "user_" + id;
        System.out.println( "key" + key + "-------------------------" );

        // 判断该key是否存在于Redis缓存中
        Boolean hasKey = redisTemplate.hasKey(key);
        ValueOperations<String, User> operations = redisTemplate.opsForValue();
        // 根据key 获取对应的Uesr对象
        User user = operations.get(key);
        // 控制台打印输出
        System.out.println("是否有缓存："+hasKey+"  缓存中的值是："+user);

        // 进行判断
        if ( hasKey ){
            // 如果该key在Redis缓存中存在 记录日志并删除对应的key-value键值对
            LOGGER.info("UserImmpl.updateUser() : 从缓存中删除user >> " + operations.get(key));
            redisTemplate.delete( key );
        }

        // 如果该对应的key不存在于Redis缓存中 则不做任何操作
        return delete;
    }



}
