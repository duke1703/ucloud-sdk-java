package cn.ucloud.udb.model;

import cn.ucloud.common.pojo.BaseResponseResult;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @Description :
 * @Author : codezhang
 * @Date : 2019-03-04 17:21
 **/
public class FetchDBPrecheckResultResult extends BaseResponseResult {

    /**
     * Result_code
     */
    @SerializedName("DataSet")
    private List<String> result;

    public List<String> getResult() {
        return result;
    }

    public void setResult(List<String> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "FetchDBPrecheckResultResult{" +
                "result=" + result +
                ", retCode=" + retCode +
                ", action='" + action + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
