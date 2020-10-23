package com.hodo.intface;

import com.hodo.bean.Json;
import com.hodo.bean.Usr;
import com.hodo.utils.net.URLProtocol;

import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.Rest;
import org.springframework.http.client.OkHttpClientHttpRequestFactory;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

import java.util.List;

/**
 * Created by gdszm on 2019/2/10.
 */

@Rest(rootUrl = URLProtocol.ROOT+":"+ URLProtocol.PORT+"/"+ URLProtocol.PROJECT+"/",converters = {GsonHttpMessageConverter.class},requestFactory = OkHttpClientHttpRequestFactory.class)
public interface UsrServiceI {
    @Post("usr/getMyAmtA?cid={cid}")
    Json getMyAmtA(@Path String cid);

    @Post("usr/getUserInfoA?cid={cid}")
    Usr getUserInfoA(@Path String cid);

    @Post("usr/updateUserAddrA?cid={cid}&addr={addr}")
    Json updateUserAddrA(@Path String cid,@Path String addr);

    @Post("usr/usrListAllA")
    List<Usr> usrListAllA();

    @Post("usr/updUsrCnameAndCpwdByCidA?cid={cid}&crealname={crealname}&cname={cname}&cpwd={cpwd}")
    Json updUsrCnameAndCpwdByCidA(@Path String cid,@Path String crealname,@Path String cname,@Path String cpwd);

}
