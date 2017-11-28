import java.util.ArrayList;
import java.util.List;

public class Feed {
	private final String title;
	private final String link;
	private final String description;
	private final String lastBuildDate;

	final List<FeedMessage> entries = new ArrayList<FeedMessage>();

	public Feed(String title, String link, String description,
			String lastBuildDate) {
		this.title = title;
		this.link = link;
		this.description = description;
		this.lastBuildDate = lastBuildDate;
	}

	public List<FeedMessage> getMessages() {
		return entries;
	}

	public String getTitle() {
		return title;
	}

	public String getLink() {
		return link;
	}

	public String getDescription() {
		return description;
	}

	public String getLastBuildDate() {
		return lastBuildDate;
	}

	public String toString() {
		return "Feed [title=" + title + ", link=" + link + ", description="
				+ description + ", lastBuildDate=" + lastBuildDate + "]";
	}
}
