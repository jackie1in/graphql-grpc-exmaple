package com.bd.post.model;

import com.bd.post.converter.LocalDateTimeConverterImpl;
import com.bd.post.converter.ProtobufNullValueInspectorImpl;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoField;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import sample.PostProto;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author <a href="mailto:hilin2333@gmail.com">created by silencecorner 2019/7/10 3:28 PM</a>
 */
@NoArgsConstructor
@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
@ProtoClass(PostProto.Post.class)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@ProtoField
    private Integer id;
	@ProtoField
    private String title;
	@ProtoField
    private String body;
	@ProtoField
    private Integer authorId;

    @CreatedDate
	@ProtoField(nullValue = ProtobufNullValueInspectorImpl.class,converter = LocalDateTimeConverterImpl.class)
    private LocalDateTime createdAt;
	@LastModifiedDate
	private LocalDateTime updatedAt;

    public Post(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public Post(String title, String body, Integer authorId) {
        this.title = title;
        this.body = body;
        this.authorId = authorId;
    }
}
