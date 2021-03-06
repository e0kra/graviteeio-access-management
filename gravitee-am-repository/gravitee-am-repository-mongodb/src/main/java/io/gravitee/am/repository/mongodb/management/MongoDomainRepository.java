/**
 * Copyright (C) 2015 The Gravitee team (http://gravitee.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.gravitee.am.repository.mongodb.management;

import io.gravitee.am.model.Domain;
import io.gravitee.am.model.login.LoginForm;
import io.gravitee.am.repository.exceptions.TechnicalException;
import io.gravitee.am.repository.management.api.DomainRepository;
import io.gravitee.am.repository.mongodb.management.internal.model.DomainMongo;
import io.gravitee.am.repository.mongodb.management.internal.model.LoginFormMongo;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
@Component
public class MongoDomainRepository extends AbstractManagementMongoRepository implements DomainRepository {

    private static final String ID_FIELD = "_id";

    @Override
    public Set<Domain> findAll() throws TechnicalException {
        return mongoOperations.findAll(DomainMongo.class)
                .stream()
                .map(this::convert)
                .collect(Collectors.toSet());
    }

    @Override
    public Optional<Domain> findById(String id) throws TechnicalException {
        return Optional.ofNullable(convert(mongoOperations.findById(id, DomainMongo.class)));
    }

    @Override
    public Set<Domain> findByIdIn(Collection<String> ids) throws TechnicalException {
        Query query = new Query();
        query.addCriteria(Criteria.where(ID_FIELD).in(ids));

        return mongoOperations.find(query, DomainMongo.class)
                .stream()
                .map(this::convert)
                .collect(Collectors.toSet());
    }

    @Override
    public Domain create(Domain item) throws TechnicalException {
        DomainMongo domain = convert(item);
        mongoOperations.save(domain);
        return convert(domain);
    }

    @Override
    public Domain update(Domain item) throws TechnicalException {
        DomainMongo domain = convert(item);
        mongoOperations.save(domain);
        return convert(domain);
    }

    @Override
    public void delete(String id) throws TechnicalException {
        DomainMongo domain = mongoOperations.findById(id, DomainMongo.class);
        mongoOperations.remove(domain);
    }


    private Domain convert(DomainMongo domainMongo) {
        if (domainMongo == null) {
            return null;
        }

        Domain domain = new Domain();
        domain.setId(domainMongo.getId());
        domain.setPath(domainMongo.getPath());
        domain.setCreatedAt(domainMongo.getCreatedAt());
        domain.setUpdatedAt(domainMongo.getUpdatedAt());
        domain.setName(domainMongo.getName());
        domain.setDescription(domainMongo.getDescription());
        domain.setEnabled(domainMongo.isEnabled());
        domain.setMaster(domainMongo.isMaster());
        domain.setLoginForm(convert(domainMongo.getLoginForm()));
        return domain;
    }

    private DomainMongo convert(Domain domain) {
        if (domain == null) {
            return null;
        }

        DomainMongo domainMongo = new DomainMongo();
        domainMongo.setId(domain.getId());
        domainMongo.setPath(domain.getPath());
        domainMongo.setCreatedAt(domain.getCreatedAt());
        domainMongo.setUpdatedAt(domain.getUpdatedAt());
        domainMongo.setName(domain.getName());
        domainMongo.setDescription(domain.getDescription());
        domainMongo.setEnabled(domain.isEnabled());
        domainMongo.setMaster(domain.isMaster());
        domainMongo.setLoginForm(convert(domain.getLoginForm()));
        return domainMongo;
    }

    private LoginForm convert(LoginFormMongo loginFormMongo) {
        if (loginFormMongo == null) {
            return null;
        }

        LoginForm loginForm = new LoginForm();
        loginForm.setEnabled(loginFormMongo.isEnabled());
        loginForm.setContent(loginFormMongo.getContent());
        loginForm.setAssets(loginFormMongo.getAssets());
        return loginForm;
    }

    private LoginFormMongo convert(LoginForm loginForm) {
        if (loginForm == null) {
            return null;
        }

        LoginFormMongo formMongo = new LoginFormMongo();
        formMongo.setEnabled(loginForm.isEnabled());
        formMongo.setContent(loginForm.getContent());
        formMongo.setAssets(loginForm.getAssets());
        return formMongo;
    }
}
