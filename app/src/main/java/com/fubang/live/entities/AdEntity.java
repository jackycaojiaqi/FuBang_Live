package com.fubang.live.entities;

import com.litesuits.orm.db.annotation.Column;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jacky on 2017/5/15.
 */
public class AdEntity {
    /**
     * status : success
     * pic_list : [{"xuhao":"http://120.26.127.210:9419/home_dhhs/Tpl/default/Room/ad/1.jpg?t=610","href":"https://www.baidu.com/"},{"xuhao":"http://120.26.127.210:9419/home_dhhs/Tpl/default/Room/ad/2.jpg?t=601","href":"http://www.163.com/"}]
     */

    private String status;
    private List<PicListBean> pic_list;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<PicListBean> getPic_list() {
        return pic_list;
    }

    public void setPic_list(List<PicListBean> pic_list) {
        this.pic_list = pic_list;
    }

    public static class PicListBean extends BaseLiteOrmEntity implements Serializable{
        /**
         * xuhao : http://120.26.127.210:9419/home_dhhs/Tpl/default/Room/ad/1.jpg?t=610
         * href : https://www.baidu.com/
         */
        @Column(value = "xuhao")
        private String xuhao;

        @Column(value = "href")
        private String href;

        public String getXuhao() {
            return xuhao;
        }

        public void setXuhao(String xuhao) {
            this.xuhao = xuhao;
        }

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }
    }
}
