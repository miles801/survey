package eccrm.base.region.service;

import com.michael.pinyin.PinYin;
import com.michael.pinyin.PinYinStrategy;
import com.michael.pinyin.SimplePinYin;
import com.michael.pinyin.StandardStrategy;
import com.ycrl.base.common.CommonStatus;
import com.ycrl.core.context.SecurityContext;
import eccrm.base.region.dao.RegionDao;
import eccrm.base.region.domain.Region;
import eccrm.base.region.domain.RegionType;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 导入行政区域
 *
 * @author miles
 * @datetime 2014/4/4 0:31
 */
public class ImportRegion {
    private RegionDao regionDao;

    static {
        SecurityContext.set("1", "unicrm", "1");
        SecurityContext.setLogin(true);
    }

    private static PinYin pinYin = new SimplePinYin();
    private static PinYinStrategy strategy = new StandardStrategy();
    private Logger logger = Logger.getLogger(ImportRegion.class);
    private SessionFactory sessionFactory;

    public ImportRegion() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public static void main(String[] args) {
        ImportRegion ir = new ImportRegion();
        try {
//            ir.importProvince();
            ir.importCity();
            ir.importDistrict();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //录入省份
    public void importProvince() throws Exception {
        logger.info("录入省份....");
        InputStream in = ImportRegion.class.getClassLoader().getResourceAsStream("Province.xml");
        Document doc = DocumentHelper.parseText(IOUtils.toString(in));
        List data = doc.selectNodes("//Provinces/Province");
        Iterator iterator = data.iterator();
        int i = 0;
        Session session = sessionFactory.openSession();
        session.setFlushMode(FlushMode.COMMIT);
        Transaction transaction = session.beginTransaction();
        while (iterator.hasNext()) {
            Node node = (Node) iterator.next();
            String id = node.valueOf("@ID");
            String name = node.valueOf("@ProvinceName");
            Region region = new Region();
            region.setPinyin(pinYin.toPinYin(name, strategy));
            region.setJp(getJP(name));
            logger.info("保存省份[" + (i + 1) + "]：" + id + "--" + name);
            region.setName(name);
            region.setId(id);
            region.setSequenceNo(i);
            region.setStatus(CommonStatus.ACTIVE.getValue());
            region.setType(RegionType.PROVINCE.getValue());
            region.setLeaf(false);
            session.save(region);
            i++;
            if (i % 10 == 0) {
                session.flush();
                session.clear();
            }
        }
        transaction.commit();
        session.close();
        logger.info("省份:一共插入了[" + i + "]条数据!");
    }

    public void importCity() throws Exception {
        logger.info("录入城市....");
        Map<String, String> regions = new HashMap<String, String>();
        // 加载城市区号
        List<String> lines = IOUtils.readLines(ImportRegion.class.getClassLoader().getResourceAsStream("region.txt"));
        for (String line : lines) {
            if (StringUtils.isEmpty(line)) {
                continue;
            }
            String[] keys = line.trim().split("\\s+");
            regions.put(keys[0], keys[1]);
        }
        InputStream in = ImportRegion.class.getClassLoader().getResourceAsStream("City.xml");
        Document doc = DocumentHelper.parseText(IOUtils.toString(in));
        List data = doc.selectNodes("//Cities/City");
        Iterator iterator = data.iterator();
        int i = 0;
        int sequenceNo = 1;
        String oldParent = null;
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        while (iterator.hasNext()) {
            Node node = (Node) iterator.next();
            String id = node.valueOf("@ID");
            String name = node.valueOf("@CityName");
            String provinceId = node.valueOf("@PID");
            if (!provinceId.equals(oldParent)) {
                sequenceNo = 1;
                oldParent = provinceId;
            }
            String zipcode = node.valueOf("@ZipCode");
            Region region = new Region();
            region.setId((1000 + Integer.parseInt(id)) + "");
            region.setName(name);
            logger.info("保存城市[" + (i + 1) + "]：" + id + "--" + name + ",所属省份：" + provinceId);
            region.setPinyin(pinYin.toPinYin(name, strategy));
            region.setJp(getJP(name));
            region.setCode(regions.get(name));// 设置区号
            Region parent = new Region();
            parent.setId(provinceId);
            region.setParent(parent);
            region.setZipcode(zipcode);
            region.setSequenceNo(sequenceNo++);
            region.setType(RegionType.CITY.getValue());
            region.setStatus(CommonStatus.ACTIVE.getValue());
            region.setLeaf(false);
            session.save(region);
            if (i % 10 == 0) {
                session.flush();
                session.clear();
            }
            i++;
        }
        session.getTransaction().commit();
        session.close();
        logger.info("城市:一共插入了[" + i + "]条数据!");
    }

    public void importDistrict() throws Exception {
        logger.info("录入区县....");
        InputStream in = ImportRegion.class.getClassLoader().getResourceAsStream("District.xml");
        Document doc = DocumentHelper.parseText(IOUtils.toString(in));
        List data = doc.selectNodes("//Districts/District");
        Iterator iterator = data.iterator();
        int i = 0;
        int sequenceNo = 1;
        String oldParent = null;
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        while (iterator.hasNext()) {
            Node node = (Node) iterator.next();
            String id = node.valueOf("@ID");
            String name = node.valueOf("@DistrictName");
            String cityId = node.valueOf("@CID");
            if (!cityId.equals(oldParent)) {
                sequenceNo = 1;
                oldParent = cityId;
            }
            Region region = new Region();
            region.setId((10000 + Integer.parseInt(id)) + "");
            region.setName(name);
            logger.info("保存区县[" + (i + 1) + "]：" + id + "--" + name + ",所属城市：" + cityId);
            region.setPinyin(pinYin.toPinYin(name, strategy));
            region.setJp(getJP(name));
            Region parent = new Region();
            parent.setId((1000 + Integer.parseInt(cityId)) + "");
            region.setParent(parent);
            region.setSequenceNo(sequenceNo++);
            region.setType(RegionType.DISTRICT.getValue());
            region.setLeaf(true);
            region.setStatus(CommonStatus.ACTIVE.getValue());
            i++;
            session.save(region);
            if (i % 10 == 0) {
                session.flush();
                session.clear();
            }
        }
        session.getTransaction().commit();
        session.close();
        logger.info("区县:一共插入了[" + i + "]条数据!");

    }

    private String getJP(String words) {
        String jp = "";
        char w[] = words.toCharArray();
        for (char foo : w) {
            jp += pinYin.toPinYin(new String(new char[]{foo}), strategy).substring(0, 1).toUpperCase();
        }
        return jp;

    }
}
