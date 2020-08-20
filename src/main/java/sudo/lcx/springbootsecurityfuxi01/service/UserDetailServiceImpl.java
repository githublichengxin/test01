package sudo.lcx.springbootsecurityfuxi01.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author lichengxin
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //s 输入的用户名
        //存放角色的集合
        Collection<GrantedAuthority> roles =new ArrayList<>();
        String admin="admin";
        String user="user";
        if(!s.equals(admin)&&!s.equals(user)){
            System.out.println("该用户不存在");
            throw new UsernameNotFoundException("该用户不存在");
        }
        if(s.equals(admin)){
            //角色  admin
            roles.add(new SimpleGrantedAuthority("admin"));
            return new User("admin","admin",roles);
        }else if(s.equals(user)){
            //角色  user
            roles.add(new SimpleGrantedAuthority("user"));
            return new User("user","user",roles);
        }
        return null;
    }
}
