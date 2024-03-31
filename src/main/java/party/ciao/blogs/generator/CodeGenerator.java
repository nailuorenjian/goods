package party.ciao.blogs.generator;

import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;

import java.util.Collections;


public class CodeGenerator {
    public static void main(String[] args) {
        String url = "jdbc:mysql:///goods";
        String username = "root";
        String password = "1056017312";
        String moduleName = "";
        String tables = "user,goods,goodstype,record,menu,storage";
        String mapperLocation = "/Users/user/Documents/blogs/src/main/resource/mapper" + moduleName;

        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author("kuw") // 设置作者
//                            .enableSwagger() // 开启 swagger 模式
//                            .fileOverride() // 覆盖已生成文件
                            .outputDir("/Users/user/Documents/blogs/src/main/java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("party.ciao.blogs") // 设置父包名
                            .moduleName(moduleName) // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, mapperLocation)); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude(tables) // 设置需要生成的表名
                            .addTablePrefix(""); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
