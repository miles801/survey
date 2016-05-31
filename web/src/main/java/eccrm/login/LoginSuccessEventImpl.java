package eccrm.login;

import com.ycrl.core.SystemContainer;
import com.ycrl.utils.string.StringUtils;
import eccrm.base.auth.domain.AccreditData;
import eccrm.base.auth.domain.AccreditFunc;
import eccrm.base.auth.service.AccreditDataService;
import eccrm.base.auth.service.AccreditFuncService;
import eccrm.base.auth.service.PersonalPermissionContext;
import eccrm.base.position.service.PositionEmpService;
import eccrm.base.user.service.LoginSuccessEvent;
import eccrm.base.user.vo.UserVo;
import eccrm.knowledge.survey.service.SurveyReportService;
import eccrm.knowledge.survey.vo.SurveyReportVo;
import eccrm.utils.NetUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Michael
 */
@Service("loginSuccessEvent")
public class LoginSuccessEventImpl implements LoginSuccessEvent {
    private Logger logger = Logger.getLogger(LoginSuccessEventImpl.class);

    @Override
    public void execute(HttpServletRequest request, UserVo userVo) {

        String empId = userVo.getEmployeeId();
        SystemContainer systemContainer = SystemContainer.getInstance();
        AccreditFuncService funcService = systemContainer.getBean(AccreditFuncService.class);
        if (funcService == null) {
            throw new RuntimeException("无法获得" + AccreditFuncService.class.getName() + "的实例对象，无法查询个人权限!");
        }
        PositionEmpService positionEmpService = systemContainer.getBean(PositionEmpService.class);
        if (positionEmpService == null) {
            throw new RuntimeException("无法获得" + PositionEmpService.class.getName() + "的实例对象，无法查询个人权限!");
        }
        HttpSession session = request.getSession();

        String employeeName = userVo.getEmployeeName();

        String ip = NetUtils.getClientIpAddress(request);
        logger.info("[" + ip + ":" + userVo.getUsername() + "(" + employeeName + ")] login success!");

        List<SurveyReportVo> data = SystemContainer.getInstance().getBean(SurveyReportService.class).queryAllOnlineIP();
        if (data != null && data.size() > 0) {
            for (SurveyReportVo vo : data) {
                Assert.isTrue(!StringUtils.equals(empId, vo.getEmpId()), "登录失败!该用户已经在线，请与管理员联系!");
                Assert.isTrue(!StringUtils.equals(ip, vo.getIp()), "登录失败!当前IP已经被其他用户占用，请与管理员联系!");
            }
        }
        // 查询员工所有岗位被授权的操作资源的编号(用于判断用户是否具有某些权限）
        Map<String, Boolean> positionResourceMap = new HashMap<String, Boolean>();
        List<String> resourceCodes = funcService.queryPersonalResourceCode();
        for (String rc : resourceCodes) {
            positionResourceMap.put(rc, true);
        }
        session.setAttribute(AccreditFunc.ACCREDIT_FUNCTION_CODE, positionResourceMap);

        // 查询个人被授权的所有数据权限的编号集合
        AccreditDataService ads = systemContainer.getBean(AccreditDataService.class);
        if (ads != null) {
            List<AccreditData> accreditData = ads.queryPersonalAllDataResource(empId);
            // key为数据资源的编号，value为数据资源对应的授权信息
            Map<String, List<AccreditData>> accreditDataMap = new HashMap<String, List<AccreditData>>();
            if (accreditData != null && !accreditData.isEmpty()) {
                for (AccreditData foo : accreditData) {
                    String code = foo.getResourceCode();
                    List<AccreditData> tmp = accreditDataMap.get(code);
                    if (tmp == null) {
                        tmp = new ArrayList<AccreditData>();
                        accreditDataMap.put(code, tmp);
                    }
                    tmp.add(foo);
                }
                session.setAttribute(PersonalPermissionContext.DATA_RESOURCE, accreditDataMap);
            }
        }
    }
}
