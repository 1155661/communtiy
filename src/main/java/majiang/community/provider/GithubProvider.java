package majiang.community.provider;

import com.alibaba.fastjson.JSON;
import majiang.community.dto.AccessTokenDTO;
import majiang.community.dto.GithubUserDTO;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * * +----------------------------------------------------------------------
 * * | 广西西途比网络科技有限公司 www.c2b666.com
 * * +----------------------------------------------------------------------
 * * | 功能描述: 请输入描述
 * * +----------------------------------------------------------------------
 * * | 时　　间: 2019/6/4 11:16
 * * +----------------------------------------------------------------------
 * * | 代码创建: 莫祖能 <1468033693@qq.com>
 * * +----------------------------------------------------------------------
 * * | 版本信息: V1.0.0
 * * +----------------------------------------------------------------------
 * * | 代码修改:（修改人 - 修改时间）
 * * +----------------------------------------------------------------------
 **/
@Component
public class GithubProvider {

    public String getAccessToken(AccessTokenDTO accessTokenDTO){

        MediaType mediaType = MediaType.get("application/json;charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()){
            String s = response.body().string();

            String[] split = s.split("&");
            String tokenStr = split[0];
            String token = tokenStr.split("=")[1];
//            System.out.println(s);
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public GithubUserDTO userDTO(String accessToken) {

        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();
        try {
            Response response = httpClient.newCall(request).execute();
            String string = response.body().string();
            GithubUserDTO userDTO = JSON.parseObject(string,GithubUserDTO.class);
            return userDTO;
        } catch (IOException e) {
        }

        return null;
    }

}
