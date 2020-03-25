使用BaseService时，可以对单表进行简单的增、删除、修改、查询，
但需要为查询的实体类建立一个继承自Mapper或者IMyMapper的接口类，不然在BaseService中无法获取mapper对象注入，
个人猜测，

@Autowired
private IBaseService<DictEntity> service;

@Service
public class LoginService extends BaseService<AccountMacEntity> implements ILoginService{

上述两种方式应是，在构造service对象时，会根据泛型的实体类，获取与实体类相关的mapper对象,
而mapper对象如

@Mapper
public interface ITestMapper extends IBaseMapper<AccountMacEntity>

@Mapper
public interface IDictMapper extends IBaseMapper<DictEntity>

通过@Mapper注解会在spring容器初始化时构造成对象，

所以使用BaseService时，只要实体类有相关的泛型Mapper，则会在容器中查找到它，并注入到service中。

