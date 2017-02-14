package ca.on.gov.gsc.s2i.e2e.iac.model;

public class PostCodeCheckData {
	private String id;
	private String desc;
	
	private PostCodeModel postCodeResidential;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public PostCodeModel getPostCodeResidential() {
		return postCodeResidential;
	}

	public void setPostCodeResidential(PostCodeModel postCodeResidential) {
		this.postCodeResidential = postCodeResidential;
	}
	
	
}
