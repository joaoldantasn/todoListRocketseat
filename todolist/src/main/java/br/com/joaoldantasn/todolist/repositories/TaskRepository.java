package br.com.joaoldantasn.todolist.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.joaoldantasn.todolist.model.TaskModel;

public interface TaskRepository extends JpaRepository<TaskModel, UUID> {

}
