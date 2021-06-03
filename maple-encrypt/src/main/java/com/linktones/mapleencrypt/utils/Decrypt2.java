 package com.linktones.mapleencrypt.utils;


 import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.util.Arrays;


 @Slf4j
  public class Decrypt2 implements FileUtilInterface
  {

    private String[] images={"jpg","png","bmp","gif"};

    private String[] videos={"mp4"};

    /*输出路径*/
    private String outputPath;

    /*public static File DecryptFile(String src) {
      File f = FileUtil.getSourcesFile(src);
      String DecryptName = src;
      DecryptName = EncryptMD5.getMD5(DecryptName.trim());
      File outpFile = decryptFile(f, GetProjectPath.getRootPath() + "\\cache\\" + DecryptName);

      return outpFile;
    }*/

    public static File decryptFile(File file, String outputPath)
    {
      FileInputStream fis = null;
      FileOutputStream fos = null;
      byte[] buff = new byte[1024];
      byte[] temp = new byte[1024];
      int i = 0; int j = 0;
      File outpFile = new File(outputPath);
      if (outpFile.exists()) {
        outpFile.delete();
      }
      try
      {
        File dirObj=new File(outpFile.getParent());
        dirObj.mkdirs();
        outpFile.createNewFile();
        fis = new FileInputStream(file);
        fos = new FileOutputStream(outpFile);
        while ((i = fis.read(buff)) > 0)
        {
          if (j % 2 == 0) {
            for (int num = 0; num < buff.length; num++) {
              temp[num] = buff[(buff.length - 1 - num)];
            }
            fos.write(temp);

            j++;
          }
          else {
            for (int num = 0; num < buff.length; num++)
            {
              buff[num] = ((byte)(1024 - buff[num]));
            }
            fos.write(buff);
            j++;
          }
        }
      }
      catch (FileNotFoundException e)
      {
        e.printStackTrace();
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
      finally
      {
        try
        {
          if (fis != null)
            fis.close();
          if (fos != null)
            fos.close();
        }
        catch (IOException e) {
          e.printStackTrace();
        }

      }

      return outpFile;
    }

    @Override
    public String doWithPath(String filePath) {
      String result="";
       /*路径转换，'\'换成‘/’*/
      filePath=filePath.replace("\\","/");
      //判断类型
      String resType = getResType(filePath);
      log.info("资源[{}]的类型:{}",filePath,resType);
      if("image".equals(resType)){

        File f=new File(filePath);
        String outputFullPath=outputPath+"/"+filePath.substring(filePath.indexOf("upload/"));
        decryptFile(f,outputFullPath);
        result=filePath+"处理完成";
      }else{
        result=filePath+"不是图片资源，跳过";
      }

      return result;
    }

    @Override
    public void init(Object obj) {
      if(null!=obj){
        outputPath=(String)obj;
      }
    }


    public static void main(String[] args) {
      String filePath="f:/aaa/upload/YDS/0/1111/aaa.jpg";
      String outputPath="e:/output";
      String outputFullPath=outputPath+"/upload/"+filePath.substring(filePath.indexOf("upload/"));
      System.out.println(outputFullPath);
    }

    public String getResType(String resPath){
      String s = StringUtils.substringAfterLast(resPath, ".");
      if(Arrays.asList(images).stream().anyMatch(x -> x.equalsIgnoreCase(s))){
        return "image";
      }else if(Arrays.asList(videos).stream().anyMatch(x -> x.equalsIgnoreCase(s))){
        return "video";
      }
      return null;
    }
  }

