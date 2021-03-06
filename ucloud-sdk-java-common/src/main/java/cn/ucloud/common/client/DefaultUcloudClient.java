package cn.ucloud.common.client;

import cn.ucloud.common.handler.UcloudHandler;
import cn.ucloud.common.http.UcloudHttp;
import cn.ucloud.common.http.UcloudHttpImpl;
import cn.ucloud.common.model.GetProjectListParam;
import cn.ucloud.common.model.GetProjectListResult;
import cn.ucloud.common.model.GetRegionParam;
import cn.ucloud.common.model.GetRegionResult;
import cn.ucloud.common.pojo.BaseRequestParam;
import cn.ucloud.common.pojo.BaseResponseResult;
import cn.ucloud.common.pojo.UcloudConfig;

/**
 * @description: 默认的ucloud基础服务client
 * @author: codezhang
 * @date: 2018-11-13 13:52
 **/

public class DefaultUcloudClient implements UcloudClient {
    protected UcloudConfig config;

    public DefaultUcloudClient(UcloudConfig config) {
        this.config = config;
    }

    @Override
    public GetRegionResult getRegion() throws Exception {
        UcloudHttp http = new UcloudHttpImpl(GetRegionResult.class);
        return (GetRegionResult) http.doPost(new GetRegionParam(), config, null);
    }

    @Override
    public void getRegion(UcloudHandler<GetRegionResult> handler, Boolean... asyncFlag) {
        UcloudHttp http = new UcloudHttpImpl(GetRegionResult.class);
        try {
            http.doPost(new GetRegionParam(), config, handler, asyncFlag);
        } catch (Exception e) {
        }
    }

    @Override
    public GetProjectListResult getProjectList() throws Exception {
        UcloudHttp http = new UcloudHttpImpl(GetProjectListResult.class);
        return (GetProjectListResult) http.doPost(new GetProjectListParam(), config, null);
    }

    @Override
    public void getProjectList(UcloudHandler<GetProjectListResult> handler, Boolean... asyncFlag) {
        UcloudHttp http = new UcloudHttpImpl(GetProjectListResult.class);
        try {
            http.doPost(new GetProjectListParam(), config, handler, asyncFlag);
        } catch (Exception e) {
        }
    }

    @Override
    public BaseResponseResult doAction(BaseRequestParam param,
                                       Class<? extends BaseResponseResult> clazz) throws Exception {
        UcloudHttp http = new UcloudHttpImpl(clazz);
        return (BaseResponseResult) http.doPost(param, config, null);
    }

    @Override
    public void doAction(BaseRequestParam param, Class<? extends BaseResponseResult> clazz,
                         UcloudHandler<? extends BaseResponseResult> handler, Boolean... asyncFlag) {
        UcloudHttp http = new UcloudHttpImpl(clazz);
        try {
            http.doPost(param, config, handler, asyncFlag);
        } catch (Exception e) {
        }
    }


}
