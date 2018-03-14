
package com.lcy.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 简化操作Excel，支持Excel2003、Excel2007、Excel2010
 */
public final class ExcelUtil
{
    private String[] heads;

    private String[] names;

    public String[] getHeads()
    {
        return heads;
    }

    public void setHeads(String[] heads)
    {
        this.heads = heads;
    }

    public String[] getNames()
    {
        return names;
    }

    public void setNames(String[] names)
    {
        this.names = names;
    }
    /**
     * 跳过首行表头从Excel文件的第一个Sheet读取数据<br>
     * 每行数据放入一个Map对象中<br>
     * 需要预先设置字段数组，读取数据时自动略过空行
     * 
     * @param in 文件输入流
     * @return 数据列表
     */
    public List read(InputStream in)
    {
        return read(in, 0);
    }

    /**
     * 跳过首行表头从Excel文件的指定Sheet读取数据<br>
     * 每行数据放入一个Map对象中<br>
     * 需要预先设置字段数组，读取数据时自动略过空行
     * 
     * @param in 文件输入流
     * @param sheetIndex 要读取的页码, 第一个Sheet页码为0
     * @return 数据列表
     */
    public List read(InputStream in, int sheetIndex)
    {
        // 检测字段数组
        if (names == null)
            throw new RuntimeException("Names is null");

        try
        {
            Workbook workbook = WorkbookFactory.create(in);
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            List data = new ArrayList(sheet.getLastRowNum());
            int cols = names.length;
            DataFormatter formatter = new DataFormatter();

            // 跳过首行表头，从第二行开始读取数据
            for (int i = 1, m = sheet.getLastRowNum() + 1; i < m; i++)
            {
                Row row = sheet.getRow(i);

                // 整行为空，跳过该行
                if (row == null)
                    continue;

                // 空Cell计数
                int n = 0;

                // 行数据放入Map中
                Map map = new HashMap(cols);

                for (int j = 0; j < cols; j++)
                {
                    Cell cell = row.getCell(j, Row.RETURN_BLANK_AS_NULL);

                    // 通过DataFormatter，按照文本方式取出数据
                    // Returns the formatted value of a cell as a String
                    // regardless of the cell type.
                    // When passed a null or blank cell, this method will return
                    // an empty String ("").
                    String cellValue = formatter.formatCellValue(cell);

                    if (cellValue != null && cellValue.length() > 0)
                    {
                            map.put(names[j], cellValue);
                    }
                    else
                        n++;
                }

                // 增加一行数据，过滤空行
                if (n != cols)
                    data.add(map);
            }
            return data;
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                in.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将数据写入Excel文件，默认页名为Sheet1，文件格式为xlsx，数据列表中每行数据放入一个Map对象中<br>
     * 需要预先设置字段数组和表头数组
     * 
     * @param data 数据列表
     * @param out 文件输出流
     */
    public void write(List data, OutputStream out)
    {
        write(data, out, "Sheet1");
    }

    /**
     * 将数据写入Excel文件，使用指定的页名（Excel限制页名31个字符内）<br/>
     * 文件格式为xlsx，容量1,048,576 行<br/>
     * 数据列表中每行数据放入一个Map对象中<br>
     * 需要预先设置字段数组和表头数组
     * 
     * @param data 数据列表
     * @param out 文件输出流
     * @param sheetName 指定的页名
     */
    public void write(List data, OutputStream out, String sheetName)
    {
        // 数据检测
        if (heads == null)
            throw new RuntimeException("Heads is null");
        if (names == null)
            throw new RuntimeException("Names is null");

        try
        {
            // 在 Excel 2010 和 Excel 2007 中，工作表的大小为 16,384 列 × 1,048,576 行，
            // 在 Excel 97-2003 中，工作表的大小为 256 列 × 65,536 行。
            // 在 Excel中，超出最大行列数单元格中的数据将会丢失。
            // 所以这里采用Excel 2010 和 Excel 2007的xlsx格式，容量1,048,576 行
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet(sheetName);

            // 放入首行表头
            Row row = sheet.createRow(0);
            for (int i = 0, m = heads.length; i < m; i++)
                row.createCell(i).setCellValue(heads[i]);

            // 放入数据
            for (int i = 0, m = data.size(); i < m; i++)
            {
                Map map = (Map) data.get(i);
                row = sheet.createRow(i + 1);
                for (int j = 0, n = names.length; j < n; j++)
                {
                    Object value = map.get(names[j]);

                    if (value == null)
                        value = "";

                    row.createCell(j).setCellValue(value.toString());
                }
            }
            workbook.write(out);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                out.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}