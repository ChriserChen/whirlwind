package org.example.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.example.model.InvitationCodeData;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class InvitationCodeProcessService {

    private static final ExcelWriter excelWriter = initExcelWriter();
    private static WriteSheet writeSheet = EasyExcel.writerSheet("InvitationCode").build();
    private static final AtomicInteger indexCount = new AtomicInteger(1);

    static {
        Runtime.getRuntime().addShutdownHook(
                new Thread(() -> {
                    if (excelWriter != null) {
                        excelWriter.finish();
                        log.info("资源释放完毕！！！");
                    }
                })
        );
    }

    /***
     * 处理邀请码相应结果
     * @param response
     */
    public synchronized void process(Response response) {
        if (!response.isSuccessful()) {
            return;
        }
        //1.解析结果
        String code = this.parseInvitationCode(response);
        if (StringUtils.isEmpty(code)) {
            return;
        }

        //2.持久化
        InvitationCodeData codeData = new InvitationCodeData();
        codeData.setIndex(indexCount.getAndIncrement());
        codeData.setCode(code);
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        codeData.setDate(date);
        endurance2Excel(codeData);
    }

    public static ExcelWriter initExcelWriter() {
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
        String fileName = getPath() + "InvitationCode_" + date + ".xlsx";
        return EasyExcel.write(fileName, InvitationCodeData.class).build();
    }

    private static String getPath() {
        String path = System.getProperty("user.dir")+"\\dist\\";
        File file = new File(path);
        if(!file.exists()){
            file.mkdir();
        }
        return path;
    }

    private static void endurance2Excel(InvitationCodeData... datas) {
        if (datas.length == 0) {
            return;
        }
        List<InvitationCodeData> dataList = new ArrayList<>(datas.length);
        for (InvitationCodeData codeData : datas) {
            dataList.add(codeData);
        }
        excelWriter.write(dataList, writeSheet);
    }


    /***
     * 解析邀请码
     * @param response
     * @return
     */
    private String parseInvitationCode(Response response) {
        try {
            Document document = Jsoup.parse(response.body().string());
            return document.getElementsByClass("bold").text();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
