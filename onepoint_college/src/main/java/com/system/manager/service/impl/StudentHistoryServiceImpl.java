package com.system.manager.service.impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.system.core.util.ParamUtil;
import com.system.manager.dao.KnowledgeSubclassTimeRecordDao;
import com.system.manager.dao.MainJPSubclassTimeRecordDao;
import com.system.manager.dao.MainPathSubclassTimeRecordDao;
import com.system.manager.dao.MainSubclassTimeRecordDao;
import com.system.manager.dao.StudentHistoryRecordDao;
import com.system.manager.entity.KnowledgeCourse;
import com.system.manager.entity.KnowledgeSubclassTime;
import com.system.manager.entity.MainBoutiqueseries;
import com.system.manager.entity.MainCoursePath;
import com.system.manager.entity.MainCourseWare;
import com.system.manager.entity.MainJPSubclassTime;
import com.system.manager.entity.MainPathSubclassTime;
import com.system.manager.entity.MainSubclassTime;
import com.system.manager.entity.StudentHistory;
import com.system.manager.service.KnowledgeCourseService;
import com.system.manager.service.MainBoutiqueseriesService;
import com.system.manager.service.MainCoursePathService;
import com.system.manager.service.MainCourseWareService;
import com.system.manager.service.StudentHistoryService;
import com.system.manager.web.model.StudentHistoryModel;
@Service
public class StudentHistoryServiceImpl implements StudentHistoryService {
	@Autowired
	private StudentHistoryRecordDao studentHistoryRecordDao;
	@Autowired
	private StudentHistoryService studentHistoryService;//????????????
	
	
	@Autowired
	private MainCourseWareService mainCourseWareService;//????????????
	@Autowired
	private MainSubclassTimeRecordDao mainSubclassTimeRecordDao;//????????????--??????
	
	@Autowired
	private MainCoursePathService mainCoursePathService;//????????????
	@Autowired
	private MainPathSubclassTimeRecordDao mainPathSubclassTimeRecordDao;//????????????--??????
	
	@Autowired
	private MainBoutiqueseriesService mainBoutiqueseriesService;//??????????????????
	
	@Autowired
	private MainJPSubclassTimeRecordDao mainJPSubclassTimeRecordDao;//??????????????????--??????
	
	@Autowired
	private KnowledgeCourseService knowledgeCourseService;//????????????
	@Autowired
	private KnowledgeSubclassTimeRecordDao knowledgeSubclassTimeRecordDao;//????????????--??????
	
	
	/**
	 * ??????????????????
	 * @param model
	 */
	@Override
	@Transactional
	public Integer addStudentHistory(
			StudentHistoryModel model) {
		// TODO Auto-generated method stub
		StudentHistory bean = new StudentHistory();
		ParamUtil.bindBean(bean,model);		
//		bean.setCynamicaddtime(DateUtils.getNowDateTime());
		studentHistoryRecordDao.save(bean);
		//??????????????????
		return bean.getId();
	}
	/**
	 * ?????????????????? ?????????
	 * @param model
	 */
	@Override
	@Transactional
	public Integer addStudentHistoryType(Integer id,Integer type,Integer userid) {
		// TODO Auto-generated method stub
		StudentHistory bean = new StudentHistory();
		StudentHistory stuhistory = studentHistoryRecordDao.findByinfo(id);
		if(stuhistory == null){
			if(type == 1){//????????????
				MainCourseWare type1 = mainCourseWareService.getCollege(id);
				bean.setCourseid(type1.getId());
				bean.setTitle(type1.getCoursewarename());
				bean.setType(type);
				bean.setContent("");
				bean.setCollegeimage(type1.getCollegeimage());
				bean.setImageid(type1.getCoursewareimageid());
				bean.setUserid(userid);
				bean.setAddtime(type1.getCoursewareaddtime());
				
				List<MainSubclassTime> timeInfo = mainSubclassTimeRecordDao.findByTime(type1.getId()+"");
				System.out.print(""+timeInfo.size());
				int timelong = 0;
				for (MainSubclassTime subclassTime : timeInfo) {
					int timaitem = Integer.parseInt(subclassTime.getSubclasstimenum());
					timelong=timelong+timaitem;
				}
				
				bean.setKeshi(timeInfo.size()+"");
				String timesc = (Math.round(timelong/60) + "??????" + (timelong%60) + "???" );
				bean.setShichang(timesc);
				bean.setXxpeople("68");
				bean.setRemark(type1.getRemark());
				
			}else if(type == 2){//??????
				MainCoursePath type1 = mainCoursePathService.getCollege(id);
				bean.setCourseid(type1.getId());
				bean.setTitle(type1.getCoursepathname());
				bean.setType(type);
				bean.setContent("");
				bean.setCollegeimage(type1.getCollegeimage());
				bean.setImageid(type1.getCoursewareimageid());
				bean.setUserid(userid);
				bean.setAddtime(type1.getCoursepathaddtime());
				
				List<MainPathSubclassTime> timeInfo = mainPathSubclassTimeRecordDao.findByTime(type1.getId()+"");
				System.out.print(""+timeInfo.size());
				int timelong = 0;
				for (MainPathSubclassTime subclassTime : timeInfo) {
					int timaitem = Integer.parseInt(subclassTime.getPathsubclasstimenum());
					timelong=timelong+timaitem;
				}
				
				bean.setKeshi(timeInfo.size()+"");
				String timesc = (Math.round(timelong/60) + "??????" + (timelong%60) + "???" );
				bean.setShichang(timesc);
				bean.setXxpeople("68");
				bean.setRemark(type1.getRemark());
				
			}else if(type == 3){//??????????????????
				MainBoutiqueseries type1 = mainBoutiqueseriesService.getCollege(id);
				bean.setCourseid(type1.getId());
				bean.setTitle(type1.getBoutiqueseriesname());
				bean.setType(type);
				bean.setContent("");
				bean.setCollegeimage(type1.getCollegeimage());
				bean.setImageid(type1.getBoutiqueseriesimageid());
				bean.setUserid(userid);
				bean.setAddtime(type1.getBoutiqueseriesaddtime());
				
				List<MainJPSubclassTime> timeInfo = mainJPSubclassTimeRecordDao.findByTime(type1.getId()+"");
				System.out.print(""+timeInfo.size());
				int timelong = 0;
				for (MainJPSubclassTime subclassTime : timeInfo) {
					int timaitem = Integer.parseInt(subclassTime.getJpsubclasstimenum());
					timelong=timelong+timaitem;
				}
				
				bean.setKeshi(timeInfo.size()+"");
				String timesc = (Math.round(timelong/60) + "??????" + (timelong%60) + "???" );
				bean.setShichang(timesc);
				bean.setXxpeople("68");
				bean.setRemark(type1.getRemark());
				
			}else if(type == 4){//????????????
				
			}else if(type == 5){//????????????
				KnowledgeCourse type1 = knowledgeCourseService.getCollege(id);
				bean.setCourseid(type1.getId());
				bean.setTitle(type1.getKnowledgename());
				bean.setType(type);
				bean.setContent("");
				bean.setCollegeimage(type1.getCollegeimage());
				bean.setImageid(type1.getKnowledgeimageid());
				bean.setUserid(userid);
				bean.setAddtime(type1.getKnowledgeaddtime());
				
				List<KnowledgeSubclassTime> timeInfo = knowledgeSubclassTimeRecordDao.findByTime(type1.getId());
				System.out.print(""+timeInfo.size());
				int timelong = 0;
				for (KnowledgeSubclassTime subclassTime : timeInfo) {
					int timaitem = Integer.parseInt(subclassTime.getKnowledgesubclasstimenum());
					timelong=timelong+timaitem;
				}
				
				bean.setKeshi(timeInfo.size()+"");
				String timesc = (Math.round(timelong/60) + "??????" + (timelong%60) + "???" );
				bean.setShichang(timesc);
				bean.setXxpeople("68");
				bean.setRemark(type1.getRemark());
				
			}
			
			studentHistoryRecordDao.save(bean);
		}
		
		//??????????????????
		return bean.getId();
	}

	/**
	 * ????????????????????????
	 * @param pageable
	 * @param model
	 */
	@Override
	public Page<StudentHistory> pageList(Pageable pageable,
			StudentHistoryModel model) {
		// TODO Auto-generated method stub
		return  studentHistoryRecordDao.findAll(getSpecification(model),pageable);
	}
	
	public Specification<StudentHistory> getSpecification(final StudentHistoryModel model){
		return new Specification<StudentHistory>(){
			@Override
			public Predicate toPredicate(Root<StudentHistory> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Expression<Boolean>> andPredicates = new ArrayList<Expression<Boolean>>();
				if(andPredicates.isEmpty()){
					return null;
				}else{
					Predicate predicate = cb.conjunction();
					predicate.getExpressions().addAll(andPredicates);
					return predicate;
				}
			}
		};
	}

	/**
	 * ??????????????????????????????
	 * @param pageable
	 * @param model
	 */
	@Override
	public Page<StudentHistory> findByPage(
			StudentHistoryModel model, Pageable pageable) {
		// TODO Auto-generated method stub
		
		return studentHistoryRecordDao.findAll(getSpecificationSearch(model),pageable);
	}
	
	/**
	 * ???????????????????????????
	 * @param pageable
	 * @param model
	 */
	@Override
	public List<StudentHistory> findByPageType(StudentHistoryModel model) {
		// TODO Auto-generated method stub
		return studentHistoryRecordDao.findAll(getSpecificationTypeSearch(model));
	}
	
	public Specification<StudentHistory> getSpecificationTypeSearch(final StudentHistoryModel model){
		return new Specification<StudentHistory>(){
			@Override
			public Predicate toPredicate(Root<StudentHistory> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Expression<Boolean>> andPredicates = new ArrayList<Expression<Boolean>>();
				if(andPredicates.isEmpty()){
					return null;
				}else{
					Predicate predicate = cb.conjunction();
					predicate.getExpressions().addAll(andPredicates);
					return predicate;
				}
			}
		};
	}
	
	
	public Specification<StudentHistory> getSpecificationSearch(final StudentHistoryModel model){
		return new Specification<StudentHistory>(){
			@Override
			public Predicate toPredicate(Root<StudentHistory> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Expression<Boolean>> andPredicates = new ArrayList<Expression<Boolean>>();
				Predicate namePredicate = null;
//				
//				if(null != model.getCynamictitle()){
//					namePredicate = cb.and(cb.like(root.<String>get("cynamictitle"),"%"+model.getCynamictitle()+"%"));
//					andPredicates.add(namePredicate);
//				}
//				
//				if(!ParamUtil.isEmpty(model.getBeginDate())){
//					namePredicate = cb.and(cb.greaterThan(root.<String>get("cynamicaddtime"),model.getBeginDate()));
//					andPredicates.add(namePredicate);
//				}
//				
//				if(!ParamUtil.isEmpty(model.getEndDate())){
//					namePredicate = cb.and(cb.lessThan(root.<String>get("cynamicaddtime"),model.getEndDate()));
//					andPredicates.add(namePredicate);
//				}
				if(andPredicates.isEmpty()){
					return null;
				}else{
					Predicate predicate = cb.conjunction();
					predicate.getExpressions().addAll(andPredicates);
					return predicate;
				}
			}
		};
	}
	/**
	 * ??????
	 * ?????? @Transactional ??????????????????????????????????????????????????????????????????????????? Spring ????????????????????????????????????????????????????????????
	 * ??????????????????????????????????????????????????????????????????????????? @Transactional ??????????????????????????? public ????????????????????? Spring AOP 
	 * ????????????????????????????????? protected???private ??????????????????????????????????????? @Transactional ?????????????????????????????????????????????????????????
	 * @param ids
	 */
	@Override
	@Transactional   //??????????????????????????????????????????????????????
	public void delete(String ids) {
	  if(!ParamUtil.isEmpty(ids)){
			Integer[] ides = ParamUtil.toIntegers(ids.split(","));
			for (Integer integer : ides) {
				studentHistoryRecordDao.delete(integer);
			}
	  }
	}
	
	/**
	 * ??????id????????????
	 * @param ids
	 * @return
	 */
	@Override
	public StudentHistory get(Integer id) {
		return studentHistoryRecordDao.findOne(id);
	}
	
	/**
	 *??????
	 */
	@Override
	public void editKnowledgeCourse(Integer id, StudentHistoryModel model) {
		StudentHistory bean = studentHistoryRecordDao.findOne(id);
//		bean.setKnowledgename(model.getKnowledgename());
//		bean.setCollegeimage(model.getCollegeimage());
//		bean.setKnowledgeimageid(model.getKnowledgeimageid());
		bean.setRemark(model.getRemark());
		
		studentHistoryRecordDao.save(bean);
	}
	
	/**
	 * ???????????????????????????????????????
	 * @param pageable
	 * @param model
	 */
	@Override
	public Page<Map<String, Object>> findByPagePhone(
			Integer id,StudentHistoryModel model, Pageable pageable) {
		// TODO Auto-generated method stub
		List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
		Page<StudentHistory> list =  studentHistoryRecordDao.findAll(getSpecificationSearch(model),pageable);
		for (StudentHistory claimRecord : list) {
			Map<String,Object> map = new HashMap<String, Object>();
			if(id == Integer.valueOf(claimRecord.getUserid())){
				//id
				map.put("id",claimRecord.getId());
				//??????id
				map.put("courseid",claimRecord.getCourseid());
				//??????
				map.put("title",claimRecord.getTitle());
				//????????????
				map.put("type",claimRecord.getType());
				//??????
				map.put("content",claimRecord.getContent());
				//????????????
				map.put("collegeimage",claimRecord.getCollegeimage());
				//????????????
				map.put("imageid",claimRecord.getImageid());
				//??????id
				map.put("userid",claimRecord.getUserid());
				//????????????
				map.put("addtime",claimRecord.getAddtime());
				//??????
				map.put("keshi",claimRecord.getKeshi());
				//??????
				map.put("shichang",claimRecord.getShichang());
				//????????????
				map.put("xxpeople",claimRecord.getXxpeople());
				//??????
				map.put("remark",claimRecord.getRemark());
				
				mapList.add(map);
			}
		}
		
		return new PageImpl<Map<String,Object>>(mapList, pageable,mapList.size());
	}
}
