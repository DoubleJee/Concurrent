import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Main {

    public static void main(String[] args) {
        try {
            // 获取字节码文件
            Class class1 = Class.forName("User");
            // 反射创建对象
            User user = (User) class1.newInstance();
            // 获取字段name
            Field name = class1.getDeclaredField("name");
            // 设置私有字段可见，可操作权限
            name.setAccessible(true);
            // 设置对象的字段值
            name.set(user,"666");
            Field age = class1.getDeclaredField("sex");
            age.setAccessible(true);
            age.set(user,1);
            System.out.println(user);
            // 获取对象的私有方法 参数为String类型
            Method method = class1.getDeclaredMethod("buy",String.class);
            // 设置可见
            method.setAccessible(true);
            // 调用这个对象的这个方法
            Object a = method.invoke(user, "珍珠");
            System.out.println(a);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Hello World!");
    }
}
