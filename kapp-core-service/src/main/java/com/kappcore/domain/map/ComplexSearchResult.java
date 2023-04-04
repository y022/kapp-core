package com.kappcore.domain.map;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.util.UUID;

@Data
public class ComplexSearchResult {
    private String docId;
    private String title;
    private String body;
    private String tag;
    private String saveDate;
    private String owner;

    public static void main(String[] args) {
        ComplexSearchResult searchResultVo = new ComplexSearchResult();
        searchResultVo.setDocId(UUID.randomUUID().toString().replaceAll("-",""));
        searchResultVo.setTitle("堆栈异常信息被吞掉的思考和处理");
        searchResultVo.setBody("背景与前提\n" +
                "2023-2-15上线日，在发布新版本代码之后，对内规部分历史数据进行重跑时，发现有21条数据重跑多次之后仍然失败。\n" +
                "排查\n" +
                "1、发现此问题后，立马尝试对执行日志进行观察，却未发现任何有用的信息，报错信息也是仅仅打印了执行错误这一句话，没有打印任何相关的堆栈异常。由于这块业务代码这次改动较大且上线时间紧迫，也是马上想到可能是代码中封装异常后，未打印出原始的堆栈，导致丢失了最关键的信息，一看代码果然没有log出堆栈，重大失误！\n" +
                "2、无法从日志获取有效信息之后，反复思考后，认为可能是这21条数据本身有问题，因为内规所有数据来源外部系统，由外部系统主动推送，且数据通过多个接口接受，被分散到多张表中。于是打开命令行窗口，连接到生产数据库，仔细对比数据之后，发现部分字段数据长度超长，原本这些字段在目前业务阶段上属于次要数据，只是接受后简单入库，此前也未曾关注过。外部系统推送时也大多未推送，或者也是推送默认值类似于无这种数据。这次推送了具有相当长度的有效数据，原来的字段长度就存不下了。到这里，算是排查到一个比较可靠的原因，但无法看到实际的堆栈信息，只能算其中一个原因，无法得知是否还有其它原因。遂打算在下次升级时，更新一下相关字段长度。\n" +
                "3、时间来到2-20号，新的升级窗口，更新字段长度的sql提前准备就绪。在完成字段长度更新之后，发现这21条数据仍然无法重试成功，遂重跑一下之前执行成功的数据，发现居然也是失败。果然还有其他问题存在，因为本次升级属于技术优化，变动越小越好，于是未更新代码也未添加log日志，这时仍然无法从执行日志中获取有效信息，执行又去排查数据本身，结果在扩大字段长度后，数据源本身再无任何问题，理论上是能执行成功的。这次深升级作罢。\n" +
                "4、时间来到2-22号，这次是切环境的窗口，趁着这次升级，更新了代码缺陷。而后从日志中发现时业务人员对数据配置出了问题，因为在代码业务逻辑中，需要拉取并使用需要业务人员提前一个配置好的数据。定位问题后，马上联系相关人员，重新配置。20min后，21条数据顺利重试执行成功。原来在修复字段长度时，在2-15到2-20号之间，业务人员改动了这块配置数据导致出现了新的问题。这才导致2-20号修复字段长度不够的问题之后仍然无法执行失败，并且导致之前执行成功的数据在重跑时也出现了失败的情况，至此问题解决。\n" +
                "5、回想这次生产问题，最主要的问题是自己的写代码的时候过于疏忽，忘记打印异常信息，其次是在线下环境测试时对测试数据也未严格要求，导致字段长度问题未在测试环境出现。\n" +
                "总结\n" +
                "自己的问题解决后，发现有其他同事在使用arthas排查问题，突然想到这种在代码层面堆栈未log的情况，好像是可以利用arthas来追踪最原始的异常堆栈数据，而后自己马上回到本地环境测试此方法，准备好一系列测试条件之后，测试发现方案可行，arthas是可以追踪到最原始的堆栈信息。如果一开始就用arthas去排查，即使是这种比较棘手的情况也能很快定位问题所在。只怪自己平时对arthas用的太少，只是了解一下，熟练度不高，问题出现时未想到借用三方工具来辅助排查。");
        searchResultVo.setTag("questionAndThink");
        searchResultVo.setOwner("和平");
        searchResultVo.setSaveDate("2023-02-23");
        System.out.println(JSON.toJSON(searchResultVo));
    }
}
