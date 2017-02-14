package ca.on.gov.gsc.s2i.e2e.model;

import java.util.List;

public class E2ECaseResult {
	private String id;
	private String name;
	private String desc;
	private List<E2ELoopResult> loopResultList;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public List<E2ELoopResult> getLoopResultList() {
		return loopResultList;
	}
	public void setLoopResultList(List<E2ELoopResult> loopResultList) {
		this.loopResultList = loopResultList;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

}
