package com.bllools.service.listener;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import com.bllools.util.CommonUtil;
import com.bllools.vo.Team;

public class TeamListener extends AnalysisEventListener<Team> {

    private final Map<String, String> teamPl = new HashMap<>();

    @Override
    public void invoke(Team team, AnalysisContext analysisContext) {
        CommonUtil.log("PL工号:" + team.getPl() + ", 四级团队名称:" + team.getTeam4());
        teamPl.put(team.getTeam4(), team.getPl());
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        CommonUtil.log("团队PL信息收集完成，团队PL信息总数量为" + teamPl.size());
    }

    public Map<String, String> getDataList() {
        return teamPl;
    }

}
