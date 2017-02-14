package ca.on.gov.gsc.s2i.e2e.iac.model;

public class DifferAddressData {
	private String id;
	private String desc;
	
	private CardModel card;
	private PostCodeModel postCodeResidential;
	private PostCodeModel postCodeMailing;
	private ContactModel contact;
	
	public CardModel getCard() {
		return card;
	}
	public void setCard(CardModel card) {
		this.card = card;
	}
	public PostCodeModel getPostCodeResidential() {
		return postCodeResidential;
	}
	public void setPostCodeResidential(PostCodeModel postCodeResidential) {
		this.postCodeResidential = postCodeResidential;
	}
	public PostCodeModel getPostCodeMailing() {
		return postCodeMailing;
	}
	public void setPostCodeMailing(PostCodeModel postCodeMailing) {
		this.postCodeMailing = postCodeMailing;
	}
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
	public ContactModel getContact() {
		return contact;
	}
	public void setContact(ContactModel contact) {
		this.contact = contact;
	}
	
}
