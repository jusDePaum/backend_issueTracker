package geiffel.da4.issuetracker.commentaire;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class CommentaireEmbeddedJSONSerializer extends JsonSerializer<Commentaire> {
    @Override
    public void serialize(Commentaire value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("contenu", value.getContenu());
        gen.writeStringField("url", "/commentaires/" + value.getId());
        gen.writeEndObject();
    }
}
