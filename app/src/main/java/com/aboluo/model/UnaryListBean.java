package com.aboluo.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by CJ on 2016/12/6.
 */

public class UnaryListBean {

    /**
     * IsSuccess : true
     * Message : 信息获取成功！
     * Result : null
     * ListResult : [{"GoodsName":"蓝月亮宝宝洗衣液婴儿衣物专用1kg瓶装","GoodsLogo":"/GoodsLogo/2eafec329276444986af8b40074f72d3.jpg","GoodsPicture":"/GoodsPicture/4bb7e80157564afcaf2d2a8213379902.jpg;/GoodsPicture/66967dd1b2ff455dbd69d8a86435bdef.jpg;","GoodsSub":"洋甘菊香柔软护肤","ColorId":0,"StandId":0,"ColorStandName":null,"ColorName":null,"CreateTime":"2017-05-01T14:00:00","FinishTime":"1900-01-01T00:00:00","StartTime":"1900-01-01T00:00:00","GoodsPrice":32.0,"Creator":1,"GoodsColorId":0,"GoodsId":7898,"GoodsStandColorId":0,"GoodsStandId":0,"Id":7,"IsDelete":0,"JoinCount":1,"NeedPersonCount":10,"State":1},{"GoodsName":"13372纯棉大版卡通三件套","GoodsLogo":"/GoodsLogo/d388d06770f346e09de8ac6a75137140.jpg","GoodsPicture":"/GoodsPicture/9ffa49d38a7346d5a8ca93dca969782a.jpg;/GoodsPicture/c6c24ef9a6774c50a878ca1b7adb6901.png;/GoodsPicture/9201ef1d496140c58663368709806819.png;/GoodsPicture/beb8826641c6419daab1dae8da33b5c6.png;/GoodsPicture/eb7f9ff2991f49e0b84ebd6293598e53.png;","GoodsSub":"柔软舒适 健康环保 亲肤贴身","ColorId":0,"StandId":0,"ColorStandName":null,"ColorName":null,"CreateTime":"2017-05-01T14:00:30","FinishTime":"1900-01-01T00:00:00","StartTime":"2017-05-05T01:06:53","GoodsPrice":100.0,"Creator":1,"GoodsColorId":0,"GoodsId":7761,"GoodsStandColorId":0,"GoodsStandId":0,"Id":8,"IsDelete":0,"JoinCount":0,"NeedPersonCount":20,"State":1},{"GoodsName":"磨毛可拆洗床垫 ","GoodsLogo":"/GoodsLogo/d3b3224e41bb4085aa956881ff945a8d.jpg","GoodsPicture":"/GoodsPicture/ccb1e5dcacd34ee0b18368ed61ed2001.jpg;/GoodsPicture/a4861370c5ee4728b0809b7782f619c0.png;/GoodsPicture/1f95c1d2a11647d7bc7ba53060f759ba.png;/GoodsPicture/27662cb72beb47ab8eb46908e4526208.png;/GoodsPicture/6840c1588d054450919d3bec283557c0.png;","GoodsSub":"可拆洗的床垫","ColorId":0,"StandId":0,"ColorStandName":null,"ColorName":null,"CreateTime":"2017-05-01T14:00:51","FinishTime":"1900-01-01T00:00:00","StartTime":"2017-05-05T01:06:46","GoodsPrice":43.0,"Creator":1,"GoodsColorId":0,"GoodsId":7524,"GoodsStandColorId":0,"GoodsStandId":0,"Id":9,"IsDelete":0,"JoinCount":0,"NeedPersonCount":15,"State":1},{"GoodsName":"vivo Y67 移动联通电信4G手机 双卡双待","GoodsLogo":"/GoodsLogo/7bd15cbd2263441a914457bdda7bed4d.jpg","GoodsPicture":"/GoodsPicture/c069073a8fde402eb50675f79490a0fa.jpg;/GoodsPicture/3737b1530c0d4624a45d2b807d4d1973.jpg;/GoodsPicture/adff28c2e28e45e1b51163c0501c47eb.jpg;/GoodsPicture/3e01510f7f75414a9c63ed7f8b628d90.jpg;","GoodsSub":"全网通 4GB+32GB ","ColorId":0,"StandId":0,"ColorStandName":null,"ColorName":null,"CreateTime":"2017-05-01T14:02:02","FinishTime":"1900-01-01T00:00:00","StartTime":"1900-01-01T00:00:00","GoodsPrice":1500.0,"Creator":1,"GoodsColorId":0,"GoodsId":6535,"GoodsStandColorId":0,"GoodsStandId":0,"Id":10,"IsDelete":0,"JoinCount":11,"NeedPersonCount":100,"State":1},{"GoodsName":"春装新款白领立领西装 女士正装","GoodsLogo":"/GoodsLogo/a1612dc381114fe89bfe800463faa19e.jpg","GoodsPicture":"/GoodsPicture/2c58910aae424838899074623cd8fe47.jpg;/GoodsPicture/ed7bc33861ba4f9aaee7761da7cb86a0.jpg;/GoodsPicture/42ecf38555264f468b6915177814aa4a.jpg;/GoodsPicture/d06a63dc133b46f8bc922f2a799d1159.jpg;","GoodsSub":"OL装 配裙配裤两件套","ColorId":0,"StandId":0,"ColorStandName":null,"ColorName":null,"CreateTime":"2017-05-05T01:07:44","FinishTime":"1900-01-01T00:00:00","StartTime":"2017-05-05T01:10:17","GoodsPrice":230.0,"Creator":1,"GoodsColorId":0,"GoodsId":3054,"GoodsStandColorId":0,"GoodsStandId":0,"Id":11,"IsDelete":0,"JoinCount":0,"NeedPersonCount":200,"State":1},{"GoodsName":"925纯银戒指批发爱的交织侣戒指","GoodsLogo":"/GoodsLogo/2a4100ae0089488b8567e963fc781d28.png","GoodsPicture":"/GoodsPicture/d91ad94abc1a4400800508473d87a5f3.png;/GoodsPicture/fe56b3f353b14b26b3be7f4190bbc7e9.png;/GoodsPicture/0be8f0f523464382974c7cb108c38202.png;","GoodsSub":"情侣对戒","ColorId":0,"StandId":0,"ColorStandName":null,"ColorName":null,"CreateTime":"2017-05-05T01:09:14","FinishTime":"1900-01-01T00:00:00","StartTime":"2017-05-05T01:10:21","GoodsPrice":1.0,"Creator":1,"GoodsColorId":0,"GoodsId":7965,"GoodsStandColorId":0,"GoodsStandId":0,"Id":12,"IsDelete":0,"JoinCount":0,"NeedPersonCount":30,"State":1},{"GoodsName":" S925纯银 韩版时尚蜻蜓戒指可调节指环","GoodsLogo":"/GoodsLogo/4d0292e8c2da4cdb9aee5da2e2fdb73e.png","GoodsPicture":"/GoodsPicture/942236c212b541bfac30530a72990b8e.png;/GoodsPicture/3a8c6647e781476c90cce6c577ee7851.png;/GoodsPicture/85e6d8e45753440e9d45df03b5d41664.png;","GoodsSub":"可调节","ColorId":0,"StandId":0,"ColorStandName":null,"ColorName":null,"CreateTime":"2017-05-05T01:09:31","FinishTime":"1900-01-01T00:00:00","StartTime":"2017-05-05T01:10:29","GoodsPrice":1.0,"Creator":1,"GoodsColorId":0,"GoodsId":7964,"GoodsStandColorId":0,"GoodsStandId":0,"Id":13,"IsDelete":0,"JoinCount":0,"NeedPersonCount":65,"State":1},{"GoodsName":"925纯银吊坠 韩版纯银饰品绿钻/香槟钻吊坠","GoodsLogo":"/GoodsLogo/826872563f4e410c822afe47b134ff6c.png","GoodsPicture":"/GoodsPicture/5722f97687a34cb29f26161856e9ff56.png;/GoodsPicture/2587a66394ed4ac08a43e7f7c74afd9e.png;/GoodsPicture/d15309d84ba646adb6533d4a64bde19a.png;","GoodsSub":"925银 假一罚万","ColorId":0,"StandId":0,"ColorStandName":null,"ColorName":null,"CreateTime":"2017-05-05T01:09:42","FinishTime":"1900-01-01T00:00:00","StartTime":"2017-05-05T01:10:37","GoodsPrice":1.0,"Creator":1,"GoodsColorId":0,"GoodsId":7962,"GoodsStandColorId":0,"GoodsStandId":0,"Id":14,"IsDelete":0,"JoinCount":0,"NeedPersonCount":52,"State":1},{"GoodsName":"朵拉朵尚男士海藻控油洁面皂 ","GoodsLogo":"/GoodsLogo/ccffa123220745d6ba89ba0f4dc2850e.jpg","GoodsPicture":"/GoodsPicture/321ef923d0a243e882a17dac7bbefaad.jpg;/GoodsPicture/2922a40aee7e4db29d095d8a32a83fe3.jpg;/GoodsPicture/c654e3454f404b41bf026b1bf40f69f5.jpg;","GoodsSub":"控油祛痘深层清洁","ColorId":0,"StandId":0,"ColorStandName":null,"ColorName":null,"CreateTime":"2017-05-09T13:15:46","FinishTime":"1900-01-01T00:00:00","StartTime":"2017-05-09T13:25:08","GoodsPrice":58.0,"Creator":26,"GoodsColorId":0,"GoodsId":3051,"GoodsStandColorId":0,"GoodsStandId":0,"Id":15,"IsDelete":0,"JoinCount":0,"NeedPersonCount":58,"State":1},{"GoodsName":"925纯银微镶蛇形不对称耳钉","GoodsLogo":"/GoodsLogo/011dcc71378d4caaa48e3e7d8a8c0590.png","GoodsPicture":"/GoodsPicture/8f767a8d144d4fa7988608be490ef50d.png;/GoodsPicture/54b6445824bf4efd954ffc32ff2168cc.png;/GoodsPicture/5cb56dbda6c742d2ae24bd016a3a5ffc.png;","GoodsSub":"蛇形不对称","ColorId":0,"StandId":0,"ColorStandName":null,"ColorName":null,"CreateTime":"2017-05-09T13:24:20","FinishTime":"1900-01-01T00:00:00","StartTime":"2017-05-09T13:25:14","GoodsPrice":39.0,"Creator":26,"GoodsColorId":0,"GoodsId":7961,"GoodsStandColorId":0,"GoodsStandId":0,"Id":16,"IsDelete":0,"JoinCount":0,"NeedPersonCount":39,"State":1}]
     * PageIndex : 1
     * PageSize : 5
     * RecordCount : 0
     */

    private boolean IsSuccess;
    private String Message;
    private Object Result;
    private int PageIndex;
    private int PageSize;
    private int RecordCount;
    /**
     * GoodsName : xxx
     * GoodsLogo : null
     * GoodsPicture : null
     * GoodsSub : null
     * ColorId : 0
     * StandId : 0
     * ColorStandName : null
     * ColorName : null
     * CreateTime : 1900-01-01T00:00:00
     * FinishTime : 1900-01-01T00:00:00
     * StartTime : 1900-01-01T00:00:00
     * GoodsPrice : 1
     * Creator : 1
     * GoodsColorId : 0
     * GoodsId : 3056
     * GoodsStandColorId : 0
     * GoodsStandId : 0
     * Id : 6
     * IsDelete : 0
     * JoinCount : 0
     * NeedPersonCount : 10
     * State : 2
     */

    private List<ListResultBean> ListResult;

    public boolean isIsSuccess() {
        return IsSuccess;
    }

    public void setIsSuccess(boolean IsSuccess) {
        this.IsSuccess = IsSuccess;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public Object getResult() {
        return Result;
    }

    public void setResult(Object Result) {
        this.Result = Result;
    }

    public int getPageIndex() {
        return PageIndex;
    }

    public void setPageIndex(int PageIndex) {
        this.PageIndex = PageIndex;
    }

    public int getPageSize() {
        return PageSize;
    }

    public void setPageSize(int PageSize) {
        this.PageSize = PageSize;
    }

    public int getRecordCount() {
        return RecordCount;
    }

    public void setRecordCount(int RecordCount) {
        this.RecordCount = RecordCount;
    }

    public List<ListResultBean> getListResult() {
        return ListResult;
    }

    public void setListResult(List<ListResultBean> ListResult) {
        this.ListResult = ListResult;
    }

    public static class ListResultBean implements  Parcelable{
        private String GoodsName;
        private String GoodsLogo;
        private String GoodsPicture;
        private String GoodsSub;
        private int ColorId;
        private int StandId;
        private String ColorStandName;
        private String ColorName;
        private String CreateTime;
        private String FinishTime;
        private String StartTime;
        private double GoodsPrice;
        private int Creator;
        private int GoodsColorId;
        private int GoodsId;
        private int GoodsStandColorId;
        private int GoodsStandId;
        private int Id;
        private int IsDelete;
        private int JoinCount;
        private int NeedPersonCount;
        private int State;

        protected ListResultBean(Parcel in) {
            GoodsName = in.readString();
            GoodsLogo = in.readString();
            GoodsPicture = in.readString();
            GoodsSub = in.readString();
            ColorId = in.readInt();
            StandId = in.readInt();
            ColorStandName = in.readString();
            ColorName = in.readString();
            CreateTime = in.readString();
            FinishTime = in.readString();
            StartTime = in.readString();
            GoodsPrice = in.readDouble();
            Creator = in.readInt();
            GoodsColorId = in.readInt();
            GoodsId = in.readInt();
            GoodsStandColorId = in.readInt();
            GoodsStandId = in.readInt();
            Id = in.readInt();
            IsDelete = in.readInt();
            JoinCount = in.readInt();
            NeedPersonCount = in.readInt();
            State = in.readInt();
        }

        public static final Parcelable.Creator<ListResultBean> CREATOR = new Creator<ListResultBean>() {
            @Override
            public ListResultBean createFromParcel(Parcel in) {
                return new ListResultBean(in);
            }

            @Override
            public ListResultBean[] newArray(int size) {
                return new ListResultBean[size];
            }
        };

        public String getGoodsName() {
            return GoodsName;
        }

        public void setGoodsName(String goodsName) {
            GoodsName = goodsName;
        }

        public int getState() {
            return State;
        }

        public void setState(int state) {
            State = state;
        }

        public String getGoodsLogo() {
            return GoodsLogo;
        }

        public void setGoodsLogo(String goodsLogo) {
            GoodsLogo = goodsLogo;
        }

        public String getGoodsPicture() {
            return GoodsPicture;
        }

        public void setGoodsPicture(String goodsPicture) {
            GoodsPicture = goodsPicture;
        }

        public String getGoodsSub() {
            return GoodsSub;
        }

        public void setGoodsSub(String goodsSub) {
            GoodsSub = goodsSub;
        }

        public int getColorId() {
            return ColorId;
        }

        public void setColorId(int colorId) {
            ColorId = colorId;
        }

        public int getStandId() {
            return StandId;
        }

        public void setStandId(int standId) {
            StandId = standId;
        }

        public String getColorStandName() {
            return ColorStandName;
        }

        public void setColorStandName(String colorStandName) {
            ColorStandName = colorStandName;
        }

        public String getColorName() {
            return ColorName;
        }

        public void setColorName(String colorName) {
            ColorName = colorName;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String createTime) {
            CreateTime = createTime;
        }

        public String getFinishTime() {
            return FinishTime;
        }

        public void setFinishTime(String finishTime) {
            FinishTime = finishTime;
        }

        public String getStartTime() {
            return StartTime;
        }

        public void setStartTime(String startTime) {
            StartTime = startTime;
        }

        public double getGoodsPrice() {
            return GoodsPrice;
        }

        public void setGoodsPrice(double goodsPrice) {
            GoodsPrice = goodsPrice;
        }

        public int getCreator() {
            return Creator;
        }

        public void setCreator(int creator) {
            Creator = creator;
        }

        public int getGoodsColorId() {
            return GoodsColorId;
        }

        public void setGoodsColorId(int goodsColorId) {
            GoodsColorId = goodsColorId;
        }

        public int getGoodsId() {
            return GoodsId;
        }

        public void setGoodsId(int goodsId) {
            GoodsId = goodsId;
        }

        public int getGoodsStandColorId() {
            return GoodsStandColorId;
        }

        public void setGoodsStandColorId(int goodsStandColorId) {
            GoodsStandColorId = goodsStandColorId;
        }

        public int getGoodsStandId() {
            return GoodsStandId;
        }

        public void setGoodsStandId(int goodsStandId) {
            GoodsStandId = goodsStandId;
        }

        public int getId() {
            return Id;
        }

        public void setId(int id) {
            Id = id;
        }

        public int getIsDelete() {
            return IsDelete;
        }

        public void setIsDelete(int isDelete) {
            IsDelete = isDelete;
        }

        public int getJoinCount() {
            return JoinCount;
        }

        public void setJoinCount(int joinCount) {
            JoinCount = joinCount;
        }

        public int getNeedPersonCount() {
            return NeedPersonCount;
        }

        public void setNeedPersonCount(int needPersonCount) {
            NeedPersonCount = needPersonCount;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(GoodsName);
            dest.writeString(GoodsLogo);
            dest.writeString(GoodsPicture);
            dest.writeString(GoodsSub);
            dest.writeInt(ColorId);
            dest.writeInt(StandId);
            dest.writeString(ColorStandName);
            dest.writeString(ColorName);
            dest.writeString(CreateTime);
            dest.writeString(FinishTime);
            dest.writeString(StartTime);
            dest.writeDouble(GoodsPrice);
            dest.writeInt(Creator);
            dest.writeInt(GoodsColorId);
            dest.writeInt(GoodsId);
            dest.writeInt(GoodsStandColorId);
            dest.writeInt(GoodsStandId);
            dest.writeInt(Id);
            dest.writeInt(IsDelete);
            dest.writeInt(JoinCount);
            dest.writeInt(NeedPersonCount);
            dest.writeInt(State);
        }
    }
}
