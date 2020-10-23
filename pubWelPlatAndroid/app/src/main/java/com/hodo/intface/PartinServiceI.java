package com.hodo.intface;

import com.hodo.bean.Act;
import com.hodo.bean.Json;
import com.hodo.bean.Partin;
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
public interface PartinServiceI {

    @Post("partin/reportA?actCid={actCid}&usrCid={usrCid}")
    Json reportA(@Path String actCid, @Path String usrCid);

    @Post("partin/donateA?actCid={actCid}&usrCid={usrCid}&amt={amt}")
    Json donateA(@Path String actCid, @Path String usrCid, @Path Integer amt);

    @Post("partin/partinListByA?actCid={actCid}&usrCid={usrCid}&tp={tp}")
    List<Partin> partinListByA(@Path String actCid, @Path String usrCid, @Path String tp);
}

