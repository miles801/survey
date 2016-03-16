package eccrm.base.tenement.web;

import eccrm.base.tenement.bo.DocumentBo;
import eccrm.base.tenement.domain.Document;
import eccrm.base.tenement.service.DocumentService;
import eccrm.base.tenement.vo.DocumentVo;
import com.ycrl.core.pager.PageVo;
import com.ycrl.core.web.BaseController;
import com.ycrl.utils.gson.GsonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author: miles
 * @datetime: 2014-07-01
 */

@Controller
@RequestMapping(value = {"/base/tenement/docs"})
public class DocumentCtrl extends BaseController {
    @Resource
    private DocumentService documentService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public void save(HttpServletRequest request, HttpServletResponse response) {
        Document document = GsonUtils.wrapDataToEntity(request, Document.class);
        documentService.save(document);
        GsonUtils.printSuccess(response);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public void update(HttpServletRequest request, HttpServletResponse response) {
        Document document = GsonUtils.wrapDataToEntity(request, Document.class);
        documentService.update(document);
        GsonUtils.printSuccess(response);
    }


    @ResponseBody
    @RequestMapping(value = "/get", params = {"id"}, method = RequestMethod.GET)
    public void findById(@RequestParam String id, HttpServletResponse response) {
        DocumentVo vo = documentService.findById(id);
        GsonUtils.printData(response, vo);
    }


    @ResponseBody
    @RequestMapping(value = "/query")
    public void query(HttpServletRequest request, HttpServletResponse response) {
        DocumentBo bo = GsonUtils.wrapDataToEntity(request, DocumentBo.class);
        PageVo pageVo = documentService.query(bo);
        GsonUtils.printData(response, pageVo);
    }

    /**
     * 根据租户id查询对应的文档集合
     *
     * @param tenementId 租户id
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET, params = {"tenementId"})
    public void queryByTenement(@RequestParam String tenementId, HttpServletResponse response) {
        List<DocumentVo> vos = documentService.queryByTenementId(tenementId);
        GsonUtils.printData(response, vos);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", params = {"ids"})
    public void deleteByIds(@RequestParam String ids, HttpServletResponse response) {
        String[] idArr = ids.split(",");
        documentService.deleteByIds(idArr);
        GsonUtils.printSuccess(response);
    }

}
