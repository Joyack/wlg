package com.wlg.Service.Impl;

import com.wlg.Dao.BaseDao;
import com.wlg.Dao.ResourcesRoleDao;
import com.wlg.Dao.RoleDao;
import com.wlg.Model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.wlg.Util.Contant;
import com.wlg.Service.MemberService;
import com.wlg.Service.ResourcesRoleService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;

/**
 * Created by LvLiangFeng on 2016/11/23.
 */

@Service
public class ResourcesRoleServiceImpl implements ResourcesRoleService{
    @Resource
    private ResourcesRoleDao resourcesRoleDao;
    @Resource
    private BaseDao baseDao;
    @Resource
    private MemberService memberService;
    @Resource
    private RoleDao roleDao;

    /**
    * 初始化Log4j的一个实例
    */
    private static final Logger logger = Logger
        .getLogger(ResourcesRoleServiceImpl.class);

    @Override
    @Transactional(propagation=Propagation.REQUIRED)
    public int saveResourcesRole(ResourcesRole resourcesRole) {
        this.resourcesRoleDao.saveResourcesRole(resourcesRole);
        return 1;

    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED)
    public int updateResourcesRole(ResourcesRole resourcesRole) {
        if(StringUtils.isEmpty(resourcesRole.getRoleid()))return 2;
        this.baseDao.executeSql("delete from resourcesrole where roleid='"+resourcesRole.getRoleid()+"'",null);
        String[] resids = resourcesRole.getResid().split(",");
        if(resids.length==0)return 1;
        ResourcesRole r = null;
        for(String resid : resids){
            r = new ResourcesRole();
            r.setRoleid(resourcesRole.getRoleid());
            r.setResid(resid);
            this.resourcesRoleDao.saveResourcesRole(r);
        }
        return 1;

    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED)
    public int deleteResourcesRole(ResourcesRole resourcesRole) {
        this.resourcesRoleDao.deleteResourcesRole(resourcesRole);
        return 1;

    }

    @Override
    @Transactional(propagation= Propagation.NOT_SUPPORTED,readOnly=true)
    public PageBean sreachResourcesRoleForPage(Integer page, ResourcesRole resourcesRole) {
        if(page==null)page=1;
        return memberService.queryForPage(10,page,"FROM ResourcesRole WHERE 1=1",resourcesRole,null);
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED)
    public List<Map<String,Object>> getResourcesRoleList(String userName) {
        if(StringUtils.isEmpty(userName)) return null;
        String roleName = Contant.getRoleTempName(userName);
        Role role = this.roleDao.getRoleByName(roleName);
        String sql = "SELECT r.id,r.name,r.useto,CASE WHEN rt.resid IS NULL THEN '0' ELSE '1' END AS status FROM resources r LEFT JOIN (SELECT resid FROM resourcesrole WHERE roleid=?) rt ON rt.resid = r.id  order by r.number asc";
        if(role==null){
            role = new Role();
            role.setRname(roleName);
            role.setRname(roleName);
            try{
                String id = this.roleDao.saveRole(role).toString();
                User user = this.baseDao.findUniqueByProperty(User.class,"username",userName);
                if(user==null) throw new RuntimeException("用户不存在");
                UserRole userRole = new UserRole();
                userRole.setUserid(user.getId());
                userRole.setRoleid(id);
                this.baseDao.save(userRole);
                return this.baseDao.findForJdbc(sql,id);
            }catch (RuntimeException e){
                return null;
            }
        }else{
            return this.baseDao.findForJdbc(sql,role.getId());
        }
//        return this.baseDao.findByQueryString("FROM ResourcesRole WHERE 1=1 "+ HqlParamUtil.getFieldValue(resourcesRole));
    }

    @Override
    @Transactional(propagation= Propagation.NOT_SUPPORTED,readOnly=true)
    public List<Map<String, Object>> getResourcesRoleListByPrincipal(String userName) {
        if(StringUtils.isEmpty(userName)) return null;
        String roleName = Contant.getRoleTempName(userName);
        Role role = this.roleDao.getRoleByName(roleName);
        String sql = "SELECT r.name,r.url,r.useto FROM resources r LEFT JOIN (SELECT resid FROM resourcesrole WHERE roleid=?) rt ON rt.resid = r.id where rt.resid is not null order by r.number asc";
        return this.baseDao.findForJdbc(sql,role.getId());
    }

    @Override
    @Transactional(propagation= Propagation.NOT_SUPPORTED,readOnly=true)
    public List<Map<String,Object>> getResourcesRoleList(ResourcesRole resourcesRole) {
        return this.baseDao.findForJdbc("SELECT r.*,CASE WHEN rt.resid IS NULL THEN '0' ELSE '1' END AS status FROM resources r LEFT JOIN (SELECT resid FROM resourcesrole WHERE roleid='"+resourcesRole.getRoleid()+"') rt ON rt.resid = r.id  order by r.number asc");
//        return this.baseDao.findByQueryString("FROM ResourcesRole WHERE 1=1 "+ HqlParamUtil.getFieldValue(resourcesRole));
    }

    @Override
    @Transactional(propagation= Propagation.NOT_SUPPORTED,readOnly=true)
    public String getFilterChainDefinitionsForRole() {
        StringBuffer sb = new StringBuffer("");
        List<Resources> rs = baseDao.loadAll(Resources.class);
        if(rs!=null&&rs.size()>0){
            boolean flag = false;
            List<ResourcesRole> rrs = null;
            for(Resources resources : rs){
                sb.append("/").append(resources.getUrl()).append("*").append(" = authc,roleOrFilter[");
                rrs = new ArrayList<>();
                rrs = this.baseDao.findByProperty(ResourcesRole.class,"resid",resources.getId());
                if(rrs.size()>0){
                    for(int i=0;i<rrs.size();i++){
                        Role role = this.baseDao.findUniqueByProperty(Role.class,"id",rrs.get(i).getRoleid());
                        if(role!=null){
                            if(!flag)
                                flag = true;
                            if(i==rrs.size()-1){
                                sb.append(role.getRname());
                            }else{
                                sb.append(role.getRname());
                                sb.append(",");
                            }
                        }
                    }
                }else{
                    sb.append("no_one");
                }

                if(!flag){
                    //如果资源与角色关系存在，但角色无记录，属于数据错误，但资源需要作出屏蔽
                    sb.append("no_one");
                }
                sb.append("]\n");
            }
        }
        System.out.println("****res****"+sb.toString());
        return sb.toString();
    }
}

