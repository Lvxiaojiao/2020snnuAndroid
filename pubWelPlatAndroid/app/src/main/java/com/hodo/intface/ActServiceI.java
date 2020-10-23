package com.hodo.intface;

import com.hodo.bean.Act;
import com.hodo.bean.Json;
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
public interface ActServiceI {

//    @Post("req/reqPubA?userId={userId}&tit={tit}&cont={cont}&cls={cls}&price={price}&area={area}")
//    Json reqPubA(@Path String userId, @Path String tit, @Path String cont, @Path String cls, @Path String price, @Path String area);

//
//    @Post("req/reqSearchListA?cls={cls}&key={key}")
//    List<Req> reqSearchListA(@Path String cls, @Path String key);
//
//    @Post("req/reqCheckListA")
//    List<Req> reqCheckListA();
//
//    @Post("req/orderListA")
//    List<Req> orderListA();
//
    @Post("act/actDetailA?cid={cid}")
    Act actDetailA(@Path String cid);

    @Post("act/checkA?cid={cid}&status={status}")
    Json checkA(@Path String cid,@Path String status);

    @Post("act/actPubA?usrCid={usrCid}&nm={nm}&sdateString={sdateString}&edateString={edateString}&reqAmt={reqAmt}&reqNum={reqNum}&instro={instro}&pic={pic}")
    Json actPubA(
            @Path String usrCid,
            @Path String nm,
            @Path String sdateString,
            @Path String edateString,
            @Path String reqAmt,
            @Path String reqNum,
            @Path String instro,
            @Path String pic
    );

    @Post("act/actSearchListA?cls={cls}&key={key}")
    List<Act> actSearchListA(@Path String cls,@Path String key);

    @Post("act/actMyReqListA?usrCid={usrCid}")
    List<Act> actMyReqListA(@Path String usrCid);
//

//    @Post("req/reqDetailBuyerInfoA?cid={cid}&userId={userId}")
//    Json reqDetailBuyerInfoA(@Path String cid, @Path String userId);
//
//    @Post("req/reqDetailBuyerInfoBuyerGoodA?cid={cid}&userId={userId}")
//    Json reqDetailBuyerInfoBuyerGoodA(@Path String cid, @Path String userId);
//
//    @Post("req/myGoodListA?cid={cid}")
//    Json myGoodListA(@Path String cid);
//
//    @Post("req/checkReqA?cid={cid}")
//    Json checkReqA(@Path String cid);
//
//    @Post("req/reReqA?cid={cid}&userId={userId}")
//    Json reReqA(@Path String cid, @Path String userId);
//
//    @Post("req/giveReqA?cid={cid}&userId={userId}")
//    Json giveReqA(@Path String cid, @Path String userId);
//
//    @Post("req/regiveA?cid={cid}&userId={userId}")
//    Json regiveA(@Path String cid, @Path String userId);
//
//    @Post("req/collectA?cid={cid}&userId={userId}")
//    Json collectA(@Path String cid, @Path String userId);
//
//    @Post("req/rePA?atId={cid}&content={content}")
//    Json rePA(@Path String cid, @Path String content);
//
//    @Post("req/pubPA?atId={cid}&content={content}")
//    Json pubPA(@Path String cid, @Path String content);
//
//    @Post("req/myPubReqListA?cid={cid}")
//    List<Req> myPubReqListA(@Path String cid);

   
}

