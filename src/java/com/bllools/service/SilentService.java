package com.bllools.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import com.bllools.service.listener.DeveloperListener;
import com.bllools.service.listener.SilentAchievementListener;
import com.bllools.service.listener.TeamListener;
import com.bllools.util.CommonUtil;
import com.bllools.vo.Developer;
import com.bllools.vo.SilentExcelModel;
import com.bllools.vo.Team;
import com.bllools.vo.UadpAchievement;
import org.apache.commons.collections4.CollectionUtils;

import com.alibaba.excel.EasyExcel;

public class SilentService {
    // 存放人员代码量完成情况
    private final List<UadpAchievement> uadpList = new ArrayList<>();

    // 存放人员信息
    private List<Developer> developers = Collections.emptyList();

    // 存放团队名称 与 PL的映射关系
    private Map<String, String> teamPl = Collections.emptyMap();

    /**
     * 静默达成指标录入
     */
    public void dealWithFile(File file) throws FileNotFoundException {
        if (file.getName().startsWith("UADP_") && file.getName().endsWith(".xlsx")) {
            SilentAchievementListener listener = new SilentAchievementListener();
            listener.init();
            EasyExcel.read(new BufferedInputStream(new FileInputStream(file)), listener)
                .head(UadpAchievement.class)
                .sheet()
                .doReadSync();
            uadpList.addAll(listener.getDataList());
        } else if (Pattern.compile("[0-9]*\\.xlsx").matcher(file.getName()).matches()) {
            DeveloperListener listener = new DeveloperListener();
            EasyExcel.read(new BufferedInputStream(new FileInputStream(file)), listener)
                .head(Developer.class)
                .sheet()
                .doReadSync();
            developers = listener.getDataList();
        } else if (file.getName().contains("PL")) {
            TeamListener listener = new TeamListener();
            EasyExcel.read(new BufferedInputStream(new FileInputStream(file)), listener)
                .head(Team.class)
                .sheet()
                .doReadSync();
            teamPl = listener.getDataList();
        }
    }

    public void process(String path) {
        if (CollectionUtils.isEmpty(developers)) {
            CommonUtil.log("缺少人员信息，或者人员信息文件不满足 202110261559.xlsx 这种格式");
        }
        // 工号 与 最终模型映射
        Map<String, SilentExcelModel> map = new HashMap<>();
        for (Developer developer : developers) {
            SilentExcelModel silentExcelModel = new SilentExcelModel();
            String w3 = developer.getW3().toLowerCase(Locale.US);
            silentExcelModel.setW3(w3);
            silentExcelModel.setName(developer.getName());
            silentExcelModel.setType(developer.getType());
            silentExcelModel.setTeam4(developer.getTeam4());
            silentExcelModel.setPl(teamPl.get(developer.getTeam4()));
            map.put(w3, silentExcelModel);
        }

        for (UadpAchievement uadpAchievement : uadpList) {
            int week = uadpAchievement.getWeek();
            SilentExcelModel silentExcelModel = map.get(uadpAchievement.getDe());
            if (silentExcelModel == null) {
                continue;
            }
            if (week == 1) {
                silentExcelModel.setWeek1(getBigDecimal(uadpAchievement, silentExcelModel.getWeek1()));
            } else if (week == 2) {
                silentExcelModel.setWeek2(getBigDecimal(uadpAchievement, silentExcelModel.getWeek2()));
            } else if (week == 3) {
                silentExcelModel.setWeek3(getBigDecimal(uadpAchievement, silentExcelModel.getWeek3()));
            } else if (week == 4) {
                silentExcelModel.setWeek4(getBigDecimal(uadpAchievement, silentExcelModel.getWeek4()));
            } else if (week == 5) {
                silentExcelModel.setWeek5(getBigDecimal(uadpAchievement, silentExcelModel.getWeek5()));
            }
        }

        List<SilentExcelModel> silentExcelModels = new ArrayList<>(map.values());
        for (SilentExcelModel silentExcelModel : silentExcelModels) {
            BigDecimal target = BigDecimal.valueOf("合作方".equals(silentExcelModel.getType()) ? 0.32 : 0.4);
            compareAndCount(silentExcelModel.getWeek1(), target, silentExcelModel);
            compareAndCount(silentExcelModel.getWeek2(), target, silentExcelModel);
            compareAndCount(silentExcelModel.getWeek3(), target, silentExcelModel);
            compareAndCount(silentExcelModel.getWeek4(), target, silentExcelModel);
            compareAndCount(silentExcelModel.getWeek5(), target, silentExcelModel);
        }

        String month = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).substring(0, 7);
        File excelFile = new File(path, month + "月静默达成.xlsx");
        EasyExcel.write(excelFile, SilentExcelModel.class).sheet("静默达成").doWrite(silentExcelModels);
        CommonUtil.log("静默达成数据处理完成！");
        CommonUtil.log("文件路径为" + excelFile.getAbsolutePath());
    }

    private void compareAndCount(BigDecimal achieves, BigDecimal target, SilentExcelModel silentExcelModel) {
        if (achieves.compareTo(target) >= 0) {
            silentExcelModel.setCount(silentExcelModel.getCount() + 1);
        }
    }

    private BigDecimal getBigDecimal(UadpAchievement uadpAchievement, BigDecimal work) {
        if (work == null) {
            return BigDecimal.valueOf(uadpAchievement.getEstimated());
        } else {
            return work.add(BigDecimal.valueOf(uadpAchievement.getEstimated()));
        }
    }
}
