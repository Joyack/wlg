package com.wlg.Service.Impl;

import com.wlg.Dao.*;
import com.wlg.Model.PageBean;

import java.util.*;

import com.wlg.Model.Permission;
import com.wlg.Util.ChineseUtil;
import com.wlg.Util.HqlParamUtil;
import com.wlg.Model.Resources;
import com.wlg.Service.MemberService;
import com.wlg.Service.ResourcesService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;

/**
 * Created by LvLiangFeng on 2016/11/23.
 */

@Service
public class ResourcesServiceImpl implements ResourcesService{
    @Resource
    private ResourcesDao resourcesDao;
    @Resource
    private BaseDao baseDao;
    @Resource
    private MemberService memberService;
    @Resource
    private MemberDao memberDao;

    @Resource
    private PermissionDao permissionDao;
    /**
    * 初始化Log4j的一个实例
    */
    private static final Logger logger = Logger
        .getLogger(ResourcesServiceImpl.class);

    @Override
    @Transactional(propagation=Propagation.REQUIRED)
    public int saveResources(Resources resources) {
        resources.setSn(ChineseUtil.getFirstSpell(resources.getResourcename()).toUpperCase());
        this.resourcesDao.saveResources(resources);
        return 1;

    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED)
    public int updateResources(Resources resources) {
        Resources r = this.baseDao.findUniqueByID(Resources.class,resources.getId());
        if(r==null)return 2;
        r.setResourcename(resources.getResourcename());
        r.setUrl(resources.getUrl());
        r.setSn(ChineseUtil.getFirstSpell(r.getResourcename()).toUpperCase());
        r.setNumber(resources.getNumber());
        this.resourcesDao.updateResources(r);
        return 1;

    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED)
    public int deleteResources(Resources resources) {
        Resources r = this.baseDao.findUniqueByID(Resources.class,resources.getId());
        if(r==null)return 2;
        this.resourcesDao.deleteResources(r);
        return 1;

    }

    @Override
    @Transactional(propagation= Propagation.NOT_SUPPORTED,readOnly=true)
    public PageBean sreachResourcesForPage(Integer page, Resources resources) {
        if(page==null)page=1;
        return memberService.queryForPage(10,page,"FROM Resources WHERE 1=1",resources,null);
    }

    @Override
    @Transactional
    public List<Resources> queryResourceByRoleId(String roleId) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT r.* FROM resources r JOIN resc_role rr ON(r.ID=rr.resid) WHERE rr.roleid='"+roleId+"'");
        System.out.println(sql.toString());
       // return baseDao.findListbySql(sql.toString());
        return memberDao.queryForPageByParams(0,1000,sql.toString()).getResult();
    }

    @Override
    @Transactional(propagation= Propagation.NOT_SUPPORTED,readOnly=true)
    public List<Resources> getResourcesList(Resources resources) {
        return this.baseDao.findByQueryString("FROM Resources WHERE 1=1 "+ HqlParamUtil.getFieldValue(resources));
    }





    @Override
    @Transactional
    public List<Map<String,Object>> getMenuListByUser(String username,String roleId) {
        String result = null;

       // Resources root = memberDao.queryForPageByParams(10,1,"select * from resources r where r.parentid='0'").getResult().get(0);

        //查询除根节点之外的所有资源
       String sql= "SELECT  m.`level`, " +
               "m.number, " +
               "m.parentid, " +
               "m.resourcename, " +
               "m.rooturl, " +
               "m.sn, " +
               "m.url, " +
               "m.useto, " +
               "m.id " +
               "from resources m " +
               "where m.parentid != '0' " +
               "order by m.number asc ";
        List<Map<String,Object>> menus =memberDao.queryForPageByParams(1000,1,sql).getResult();
        //根据用户查询
        String sql1="select * from (select DISTINCT p.resourceid from permission p,resources re " +
                " where re.id=p.resourceid  " +
                " and (exists(select r.id from role r where r.id=p.roleid and r.id='"+roleId+"') " +
                "     or exists(select u.id from user u where u.id=p.userid and u.username='"+username+"'))) t ";
        List<String> menuIds = memberDao.queryForPageByParams(1000,1,sql1).getResult();
        List<String> s=new ArrayList<>();
        if (menus != null && menus.size() > 0) {
            for(Object a:  menuIds.toArray()){
                s.add((a.toString()).substring(12,44));
            }
            for (Iterator<Map<String,Object>> it = menus.iterator(); it.hasNext();) {
                String menu = it.next().get("id").toString();
                if (!s.contains(menu)) {
                    it.remove();
                }
            }
        }
        List<Map<String,Object>> list_first = new ArrayList<>();
        List<Map<String,Object>> list_second = new ArrayList<Map<String,Object>>();
        if (menus != null && menus.size() > 0) {
            for (Iterator<Map<String,Object>> it = menus.iterator(); it.hasNext();) {
                Map<String,Object> menu = it.next();
                if ("2".equals(menu.get("level").toString())) {
                    list_first.add(menu);
                    it.remove();
                } else if ("3".equals(menu.get("level").toString())) {
                    list_second.add(menu);
                    it.remove();
                } else if ("1".equals(menu.get("level").toString())) {
                    it.remove();
                }
            }
        }
        if (list_first != null && list_first.size() > 0) {
            if (list_second != null && list_second.size() > 0) {
                // 装载三级菜单入二级
               // if (menus != null && menus.size() > 0) {
                    for (Iterator<Map<String,Object>> it_second = list_second.iterator(); it_second
                            .hasNext();) {
                        Map<String,Object> menu_second = it_second.next();
                        Map<String,Object> menu_list = new HashMap<>();
                        int c=0;
                        for (Iterator<Map<String,Object>> it_third = menus.iterator(); it_third
                                .hasNext();) {
                            Map<String,Object> menu_third = it_third.next();

                            if (menu_third.get("parentid").equals(
                                    menu_second.get("id"))) {
                                menu_list.put(""+c,menu_second);
                                it_third.remove();
                            }
                            c++;
                        } menu_second.put("menu_list",menu_list);
                    }
               // }
                // 装载二级菜单入一级
                for (Iterator<Map<String,Object>> it_first = list_first.iterator(); it_first
                        .hasNext();) {
                    Map<String,Object> menu_first = it_first.next();
                    Map<String,Object> menu_list = new HashMap<>();
                    int c=0;
                    for (Iterator<Map<String,Object>> it_second = list_second.iterator(); it_second
                            .hasNext();) {
                        Map<String,Object> menu_second = it_second.next();
                        if (menu_second.get("parentid")
                                .equals(menu_first.get("id").toString())) {
                            menu_list.put(""+c,menu_second);
                            it_second.remove();
                        }
                        c++;
                    } menu_first.put("menu_list",menu_list);
                }

            }
            return list_first;
        }
        return null;
    }

    @Override
    public List<Map<String,Object>> getMenuListByParentid(int page,int pageSize,String parentid) {
        StringBuffer sql=new StringBuffer("select * from resources r where r.parentid=? order by r.number asc ");
        return memberDao.queryMapsBypage(new Object[]{parentid},page,pageSize,sql.toString()).getResult();
    }
    @Override
    public List<Map<String,Object>> getAllResources(){
        StringBuffer sql=new StringBuffer("select * from resources r where 1=1 ");
        return memberDao.queryForPageByParams(1000,1,sql.toString()).getResult();
    }

    @Override
    public List<Map<String, Object>> getPermissionByRoleorUser(String ruflag,String ruid) {
        StringBuffer sql=new StringBuffer("select * from permission p where 1=1");
        if(ruflag.equals("user")){
            sql.append(" and p.userid='"+ruid+"'");
        }else if("role".equals(ruflag)){
            sql.append(" and p.roleid='"+ruid+"'");
        }
        return memberDao.queryForPageByParams(10000,1,sql.toString()).getResult();
    }



    @Override
    @Transactional
    public void saveRoleorUserRes(List<Permission> rrs) {
        if(rrs.size()>0){
            for (Permission p:rrs){
                permissionDao.savePermission(p);
            }
        }
    }

    @Override
    @Transactional
    public Resources getResourceByid(String id) {
        return baseDao.getEntity(Resources.class,id);
    }


}

