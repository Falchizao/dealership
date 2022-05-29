package com.utfpr.concessionaria.generic;

import java.util.List;
import java.util.Optional;

public abstract class IService <T extends IModel, K, L, P>{
     abstract List<K> getAll();
     abstract Optional<K> getById(Long id);
     abstract K add(T model);
     abstract void delete(Long id);
     abstract K update(K model, Long id);
}
