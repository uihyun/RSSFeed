public class FeedMessage {
	private String title;
	private String description;
	private String link;
	private String pubDate;
	private String guid;

	public FeedMessage(String title, String link, String description,
			String pubDate, String guid) {
		this.title = title;
		this.link = link;
		this.description = description;
		this.pubDate = pubDate;
		this.guid = guid;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getLink() {
		return link;
	}

	public String getPubDate() {
		return pubDate;
	}

	public String getGuid() {
		return guid;
	}

	public String toString() {
		return "FeedMessage [title=" + title + ", description=" + description
				+ ", link=" + link + ", pubDate=" + pubDate + ", guid=" + guid
				+ "]";
	}
}
