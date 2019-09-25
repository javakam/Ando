package pdf2htmlex;

import org.apache.commons.lang3.StringUtils;

/**
 * pdf转html工具类
 * <p>
 * 参考链接 : https://www.jianshu.com/p/e752dfeb628f
 *
 * @author 1nchaos
 * @date 2019/3/7 13:20
 */
public class Pdf2HtmlUtil {

    /**
     * 调用pdf2htmlEX将pdf文件转换为html文件
     *
     * @param exeFilePath  pdf2htmlEX.exe文件路径
     * @param pdfFile      pdf文件绝对路径
     * @param destDir      生成的html文件存放路径
     * @param htmlFileName 生成的html文件名称
     * @return
     */
    public static boolean pdf2html(String exeFilePath, String pdfFile, String htmlFileName, String destDir) {
        if (StringUtils.isBlank(exeFilePath) && StringUtils.isBlank(pdfFile)
                && StringUtils.isBlank(htmlFileName)) {
            System.out.println("传递的参数有误！");
            return false;
        }
        Runtime rt = Runtime.getRuntime();
        StringBuilder command = new StringBuilder();
        command.append(exeFilePath).append(" ");
        // 生成文件存放位置,需要替换文件路径中的空格
        if (!StringUtils.isBlank(destDir) && !"".equals(destDir.trim())) {
            command.append("--dest-dir ").append(destDir.replace(" ", "\" \"")).append(" ");
        }
        // 尽量减少用于文本的HTML元素的数目 (default: 0)
        command.append("--optimize-text 1 ");
        command.append("--zoom 1.4 ");
        // html中显示链接：0——false，1——true
        command.append("--process-outline 0 ");
        // 嵌入html中的字体后缀(default ttf)
        command.append("--font-format woff ");
        // 生成css目录
        command.append(String.format("--css-filename %s\\data\\fancy.css ", destDir));
        // ttf,otf,woff,svg  需要替换文件路径中的空格
        command.append(pdfFile.replace(" ", "\" \"")).append(" ");
        if (!"".equals(htmlFileName.trim())) {
            command.append(htmlFileName);
            if (!htmlFileName.endsWith(".html")) {
                command.append(".html");
            }
        }
        if (streamPrintInfo(rt, command)) return true;
        return false;
    }

    /**
     * @param pdfFile
     * @param destDir 生成的html文件存放路径
     * @return
     */
    public static boolean pdf2htmlLinux(String pdfFile, String destDir) {
        if (StringUtils.isBlank(pdfFile) && StringUtils.isBlank(destDir)) {
            System.out.println("传递的参数有误！");
            return false;
        }
        Runtime rt = Runtime.getRuntime();
        StringBuilder command = new StringBuilder();
        command.append("pdf2htmlEX").append(" ");
        // 尽量减少用于文本的HTML元素的数目 (default: 0)
        command.append("--optimize-text 1 ");
        command.append("--zoom 1.4 ");
        // html中显示链接：0——false，1——true
        command.append("--process-outline 0 ");
        // 嵌入html中的字体后缀(default ttf)
        command.append("--font-format woff ");
        // ttf,otf,woff,svg  需要替换文件路径中的空格
        command.append(pdfFile.replace(" ", "\" \"")).append(" ");
        // 生成文件存放位置,需要替换文件路径中的空格
        if (destDir != null && !"".equals(destDir.trim())) {
            command.append("--dest-dir ").append(destDir.replace(" ", "\" \"")).append(" ");
        }
        return streamPrintInfo(rt, command);
    }

    public static boolean pdf2htmlLinux(String pdfFile, String htmlFileName, String destDir) {
        if (StringUtils.isBlank(pdfFile) && StringUtils.isBlank(htmlFileName)) {
            System.out.println("传递的参数有误！");
            return false;
        }
        Runtime rt = Runtime.getRuntime();
        StringBuilder command = new StringBuilder();
        command.append("pdf2htmlEX").append(" ");
        // 生成文件存放位置,需要替换文件路径中的空格
        if (destDir != null && !"".equals(destDir.trim()))
            command.append("--dest-dir ").append(destDir.replace(" ", "\" \"")).append(" ");
        // 尽量减少用于文本的HTML元素的数目 (default: 0)
        command.append("--optimize-text 1 ");
        command.append("--zoom 1.4 ");
        // html中显示链接：0——false，1——true
        command.append("--process-outline 0 ");
        // 嵌入html中的字体后缀(default ttf)
        command.append("--font-format woff ");
        // ttf,otf,woff,svg  需要替换文件路径中的空格
        command.append(pdfFile.replace(" ", "\" \"")).append(" ");
        if (!"".equals(htmlFileName.trim())) {
            command.append(htmlFileName);
            if (!htmlFileName.endsWith(".html")) {
                command.append(".html");
            }
        }
        if (streamPrintInfo(rt, command)) return true;
        return false;
    }

    private static boolean streamPrintInfo(Runtime rt, StringBuilder command) {
        try {
            System.out.println("Command：" + command.toString());
            Process p = rt.exec(command.toString());
            StreamGobbler errorGobbler = new StreamGobbler(p.getErrorStream(), "ERROR");
            // 开启屏幕标准错误流
            errorGobbler.start();
            StreamGobbler outGobbler = new StreamGobbler(p.getInputStream(), "STDOUT");
            // 开启屏幕标准输出流
            outGobbler.start();
            int w = p.waitFor();
            int v = p.exitValue();
            if (w == 0 && v == 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}