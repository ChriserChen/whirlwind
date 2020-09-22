package org.example.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;


@Data
@ContentRowHeight(15)
@HeadRowHeight(20)
@ColumnWidth(20)
public class InvitationCodeData {
    @ExcelProperty("序号")
    private int index;
    @ExcelProperty("邀请码")
    private String code;
    @ExcelProperty("时间")
    private String date;
}
