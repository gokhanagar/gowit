package pojos.pet;

import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.List;

public class Pet {

    private int id;
    private Category category;
    private String name;
    private List<String> photoUrls;
    private List<Tag> tags;
    private String status;
    private static final Faker faker = new Faker();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPhotoUrls() {
        return photoUrls;
    }

    public void setPhotoUrls(List<String> photoUrls) {
        this.photoUrls = photoUrls;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", category=" + category +
                ", name='" + name + '\'' +
                ", photoUrls=" + photoUrls +
                ", tags=" + tags +
                ", status='" + status + '\'' +
                '}';
    }

    public static Pet createFakePet() {
        Pet pet = new Pet();
        pet.setId(faker.number().numberBetween(1, 1000));

        // Set category
        Category category = new Category();
        category.setId(faker.number().numberBetween(1, 100));
        category.setName(faker.animal().name());
        pet.setCategory(category);

        // Set name
        pet.setName(faker.dog().name());

        // Set photoUrls
        List<String> photoUrls = new ArrayList<>();
        photoUrls.add(faker.internet().url());
        photoUrls.add(faker.internet().url());
        pet.setPhotoUrls(photoUrls);

        // Set tags
        List<Tag> tags = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Tag tag = new Tag();
            tag.setId(faker.number().numberBetween(1, 50));
            tag.setName(faker.lorem().word());
            tags.add(tag);
        }
        pet.setTags(tags);

        // Set status
        String[] statuses = {"available", "pending", "sold"};
        pet.setStatus(statuses[faker.number().numberBetween(0, statuses.length)]);

        return pet;
    }


}
