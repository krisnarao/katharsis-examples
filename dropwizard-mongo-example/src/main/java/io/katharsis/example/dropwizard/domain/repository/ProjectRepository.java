package io.katharsis.example.dropwizard.domain.repository;

import io.katharsis.example.dropwizard.domain.model.Project;
import io.katharsis.example.dropwizard.managed.MongoManaged;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.ResourceRepository;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;

import javax.inject.Inject;

public class ProjectRepository implements ResourceRepository<Project, ObjectId> {

    private Datastore datastore;

    @Inject
    public ProjectRepository(MongoManaged mongoManaged) {
        datastore = mongoManaged.getDatastore();
    }

    public <S extends Project> S save(S entity) {
        Key<Project> saveKey = datastore.save(entity);
        return (S) datastore.getByKey(Project.class, saveKey);
    }

    public <S extends Project> S update(S entity) {
        return save(entity);
    }

    public Project findOne(ObjectId id) {
        return datastore.getByKey(Project.class, new Key<>(Project.class, id));
    }

    @Override
    public Iterable<Project> findAll(RequestParams requestParams) {
        return datastore.find(Project.class);
    }

    @Override
    public Iterable<Project> findAll(Iterable<ObjectId> iterable, RequestParams requestParams) {
        return datastore.get(Project.class, iterable);
    }

    public void delete(ObjectId id) {
        datastore.delete(Project.class, new Key<>(Project.class, id));
    }
}