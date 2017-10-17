import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * 生成代码 测试
 * Created by LvLiangFeng on 2016/11/21.
 */
public class generateJavaRes {
    public static void main(String[] args) throws IOException {
        //根据model名称生成Dao、DaoImpl、Service、ServiceImpl、Controller 的基础增、删、改、分页查询、多条件查询
        //项目路径、名称
        String projectUrl = "E:\\newSvnProject\\operation\\";
        //model名称，大小写区别
        String modelName = "ContractWaterPumpsDetail";
        generateJavaRes.generateDaoServiceController(projectUrl,modelName);
    }

    /**
     *
     * @param name
     * @throws IOException
     */
    public static void generateDaoServiceController(String projectUrl, String name) throws IOException {
        String casename = name.substring(0,1).toLowerCase()+name.substring(1,name.length());
        String filePath = projectUrl+"src\\main\\java\\com\\wlg\\Dao\\"+name+"Dao.java";
        File outFile = new File(filePath);
        if(!outFile.exists()){
            outFile.createNewFile();
        }
        else{
            outFile.delete();
            outFile.createNewFile();
        }
        String sr = getDaoSB.getDao(name,casename);
        String fileEncode = System.getProperty("file.encoding");
        OutputStreamWriter ow=new OutputStreamWriter(new FileOutputStream(outFile),fileEncode);
        ow.write(sr);
        ow.flush();
        ow.close();

        casename = name.substring(0,1).toLowerCase()+name.substring(1,name.length());
        filePath = projectUrl+"src\\main\\java\\com\\wlg\\Dao\\Impl\\"+name+"DaoImpl.java";
        outFile = new File(filePath);
        if(!outFile.exists()){
            outFile.createNewFile();
        }
        else{
            outFile.delete();
            outFile.createNewFile();
        }
        sr = getDaoSB.getDaoImpl(name,casename);
        fileEncode = System.getProperty("file.encoding");
        ow=new OutputStreamWriter(new FileOutputStream(outFile),fileEncode);
        ow.write(sr);
        ow.flush();
        ow.close();


        casename = name.substring(0,1).toLowerCase()+name.substring(1,name.length());
        filePath = projectUrl+"src\\main\\java\\com\\wlg\\Service\\"+name+"Service.java";
        outFile = new File(filePath);
        if(!outFile.exists()){
            outFile.createNewFile();
        }
        else{
            outFile.delete();
            outFile.createNewFile();
        }
        sr = getServiceSB.getService(name,casename);
        fileEncode = System.getProperty("file.encoding");
        ow=new OutputStreamWriter(new FileOutputStream(outFile),fileEncode);
        ow.write(sr);
        ow.flush();
        ow.close();


        casename = name.substring(0,1).toLowerCase()+name.substring(1,name.length());
        filePath = projectUrl+"src\\main\\java\\com\\wlg\\Service\\Impl\\"+name+"ServiceImpl.java";
        outFile = new File(filePath);
        if(!outFile.exists()){
            outFile.createNewFile();
        }
        else{
            outFile.delete();
            outFile.createNewFile();
        }
        sr = getServiceSB.getServiceImpl(name,casename);
        fileEncode = System.getProperty("file.encoding");
        ow=new OutputStreamWriter(new FileOutputStream(outFile),fileEncode);
        ow.write(sr);
        ow.flush();
        ow.close();

        casename = name.substring(0,1).toLowerCase()+name.substring(1,name.length());
        filePath = projectUrl+"src\\main\\java\\com\\wlg\\Controller\\"+name+"Controller.java";
        outFile = new File(filePath);
        if(!outFile.exists()){
            outFile.createNewFile();
        }
        else{
            outFile.delete();
            outFile.createNewFile();
        }
        sr = getControllerSB.getController(name,casename);
        fileEncode = System.getProperty("file.encoding");
        ow=new OutputStreamWriter(new FileOutputStream(outFile),fileEncode);
        ow.write(sr);
        ow.flush();
        ow.close();
    }
}
