package DTUTest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by Administrator on 2017/6/13 0013.
 */
public class TestDTU {

    private Log mylog = LogFactory.getLog(TestDTU.class);
    /**
     * DTU 调用方法
     * main
     * (这里描述这个方法适用条件 – 可选)
     * @param args：   void
     * author： huastone
     * 日期：2013-3-22-上午11:30:25
     * @exception
     * @version  V1.0.0
     */
    public static void main(String[] args) {
        String host_ip = "127.0.0.1";   //DTU的IP地址

        int port = 1001;       //DTU端口
        //调用
        NKLongSocket nksocket = new NKLongSocket(host_ip,port,null);
        nksocket.start();

       // NioSocektServer nioSocektServer=new NioSocektServer(8887);
        //nioSocektServer.run();
    }
}
