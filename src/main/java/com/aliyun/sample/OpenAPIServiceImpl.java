package com.aliyun.sample;


import com.aliyun.dypnsapi20170525.models.GetMobileRequest;
import com.aliyun.dypnsapi20170525.models.GetMobileResponse;
import com.aliyun.dypnsapi20170525.models.VerifyMobileRequest;
import com.aliyun.dypnsapi20170525.models.VerifyMobileResponse;
import com.aliyun.teaopenapi.models.Config;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;

/**
 * 开放API接口 服务实现类
 *
 * @author: jacklin
 * @since: 2021/03/29 9:39
 **/
@Slf4j
@Service
public class OpenAPIServiceImpl implements IOpenAPIService {

    /**
     * 使用AK&SK初始化账号Client
     **/
    public static com.aliyun.dypnsapi20170525.Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        Config config = new Config()
                .setAccessKeyId(accessKeyId)
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = "dypnsapi.aliyuncs.com";
        return new com.aliyun.dypnsapi20170525.Client(config);
    }


    /**
     * 响应内容格式:
     * {
     * "Message": "请求成功",
     * "RequestId": 8906582,
     * "Code": "OK",
     * "GetMobileResultDTO": {
     * "Mobile": 121343241
     * }
     * }
     **/
    @Override
    public GetMobileResponse getMobile(String accessToken, String outId, HttpServletRequest request) {
        com.aliyun.dypnsapi20170525.Client client = null;
        GetMobileResponse response = new GetMobileResponse();
        try {

            //从数据库查询获取短信全局配置数据，存在从缓存里取
            String accessKeyId = this.selectConfigValueByKey(CommonConstant.SMS_ACCESS_KEY_ID, request);
            String accessKeySecret = this.selectConfigValueByKey(CommonConstant.SMS_ACCESS_KEY_SECRET, request);
            if (StringUtils.isNoneEmpty(accessKeyId, accessKeySecret)) {
                client = createClient(accessKeyId, accessKeySecret);
                GetMobileRequest mobileRequest = new GetMobileRequest();
                mobileRequest.setAccessToken(accessToken);
                mobileRequest.setOutId(outId);
                response = client.getMobile(mobileRequest);
            } else {
                log.error("阿里云号码认证（一键登录）失败，获取系统配置秘钥为空!");
            }
        } catch (Exception e) {
            log.error(response.body.getMessage());
        }
        return response;
    }


    /**
     * 响应格式：
     * {
     * "Message": "请求成功",
     * "RequestId": 8906582,
     * "Code": "OK"
     * "GateVerifyResultDTO": {
     * "VerifyResult": "PASS",
     * "VerifyId": 121343241
     * }
     * }
     **/
    @Override
    public VerifyMobileResponse verifyMobile(String accessCode, String phoneNumber, String outId, HttpServletRequest request) {
        com.aliyun.dypnsapi20170525.Client client = null;
        VerifyMobileResponse verifyMobileResponse = new VerifyMobileResponse();
        try {

            //从数据库查询获取短信全局配置数据，存在从缓存里取
            String accessKeyId = this.selectConfigValueByKey(CommonConstant.SMS_ACCESS_KEY_ID, request);
            String accessKeySecret = this.selectConfigValueByKey(CommonConstant.SMS_ACCESS_KEY_SECRET, request);
            if (StringUtils.isNoneEmpty(accessKeyId, accessKeySecret)) {
                client = createClient(accessKeyId, accessKeySecret);
                VerifyMobileRequest verifyMobileRequest = new VerifyMobileRequest();
                verifyMobileRequest.setAccessCode(accessCode);
                verifyMobileRequest.setPhoneNumber(phoneNumber);
                verifyMobileRequest.setOutId(outId);
                verifyMobileResponse = client.verifyMobile(verifyMobileRequest);
            } else {
                log.error("本机号码校验认证失败，获取系统配置短信秘钥信息为空");
            }

        } catch (Exception e) {
            log.error(verifyMobileResponse.body.getMessage());
        }

        return verifyMobileResponse;
    }

    @Override
    public String selectConfigValueByKey(String configKey, HttpServletRequest request) {
//        Integer tenantId = GlobalWebSiteValue.getTenantId(request);
//        String cacheConfigKey = "TENANT_ID_" + tenantId + "_" + configKey;
//        //如果缓存存在走缓存取
//        String configValue = Convert.toStr(redisUtil.get(cacheConfigKey));
//        if (StringUtils.isNotBlank(configValue)) {
//            //log.info("短信秘钥缓存存在，从缓存获取~");
//            return configValue;
//        }
//        SystemConfigDTO systemConfigDTO = baseCommonMapper.selectSystemConfigByConfigKey(configKey);
//        if (ObjectUtil.isNotNull(systemConfigDTO)) {
//            //入缓存
//            redisUtil.set(cacheConfigKey, systemConfigDTO.getConfigValue());
//            return systemConfigDTO.getConfigValue();
//        }
//        return StringUtils.EMPTY;
        if (configKey.equals(CommonConstant.SMS_ACCESS_KEY_ID)) {
            return CommonConstant.SMS_ACCESS_KEY_ID;
        } else if (configKey.equals(CommonConstant.SMS_ACCESS_KEY_SECRET)) {
            return CommonConstant.SMS_ACCESS_KEY_SECRET;
        } else {
            return "";
        }
    }

}
