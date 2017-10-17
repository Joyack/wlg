package com.wlg.Dao;

import com.wlg.Dao.Impl.hibernate.*;
import com.wlg.Model.SysI18n;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/6/12 0012.
 */
@Service
public interface SysI18nDao{


    /**
     * get i18n string by reference id and language<br>
     * 根据引用表的主键id和语言，得到国际化资源串
     *
     * @param refid
     * @param language
     * @return
     */
    public String getName(String refid, String language);

    /**
     * get sys_i18n entity by reference id and language<br>
     * 根据引用表的主键id和语言，得到sys_i18n实体
     *
     * @param refid
     * @param language
     * @return
     */
    public SysI18n getSysI18n(String refid, String language) ;
}
