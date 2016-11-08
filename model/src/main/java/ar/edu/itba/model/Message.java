package ar.edu.itba.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "message")
public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message_id_seq")
	@SequenceGenerator(sequenceName = "message_id_seq", name = "message_id_seq", allocationSize = 1)
	private Long id;
	@ManyToOne(fetch =FetchType.EAGER, optional = false)
	private User from;
	@ManyToOne(fetch =FetchType.EAGER, optional = false)
	private User to;
	@Column (length=50, nullable=false, name= "subject")
	private String subject;
	@Column(length = 150,nullable = false, name = "message")
	private String message;
	@Column(nullable = false, name = "read")
	private boolean read;
	
	/* package */ Message(){
		
	}
	
	public Message(User from, User to, String subject, String message){
		this.from=from;
		this.to = to;
		this.subject = subject;
		this.message=message;
		this.read=false;
	}

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	public Long getId() {
		return id;
	}

	public User getFrom() {
		return from;
	}

	public User getTo() {
		return to;
	}

	public String getMessage() {
		return message;
	}

	public String getSubject(){return subject; }
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Message other = (Message) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
