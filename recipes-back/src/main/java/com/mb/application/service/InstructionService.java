/*
package com.mb.application.service;

import com.mb.application.entity.InstructionEntity;
import com.mb.application.repository.InstructionDao;
import com.mb.application.repository.RecipeDao;
import com.mb.server.model.Instruction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InstructionService {

    private static final Logger LOG = LoggerFactory.getLogger(RecipeService.class);

    @Autowired
    InstructionDao instructionDao;

    @Autowired
    RecipeDao recipeDao;

    public List<Instruction> listInstructions() {

        return instructionDao.findAll().stream().map(this::buildInstructionModel).collect(Collectors.toList());
    }

    public Instruction getInstruction(String id) {
        Optional<InstructionEntity> instructionEntity = instructionDao.findById(Integer.valueOf(id));
        if (instructionEntity.isEmpty()) {
            return null;
        }
        return this.buildInstructionModel(instructionEntity.get());
    }

    public Instruction createInstruction(Instruction instruction) {
        InstructionEntity instructionEntity = new InstructionEntity();
        instructionEntity.setDescription(instruction.getDescription());

        InstructionEntity created = instructionDao.save(instructionEntity);
        return buildInstructionModel(created);
    }

    public int updateInstruction(String id, Instruction instruction) {
        InstructionEntity instructionEntity = new InstructionEntity();

        instructionEntity.setId(instruction.getId());
        instructionEntity.setDescription(instruction.getDescription());
        instructionEntity.setPos(instruction.getPosition());

        int updatedId = instructionDao.save(instructionEntity).getId();
        return updatedId;

    }

    public void deleteRecipe(String id) {
        Instruction recipe = getInstruction(id);
        if (recipe == null) {
            return;
        }
        instructionDao.deleteById(Integer.valueOf(id));
    }

    public Instruction buildInstructionModel(InstructionEntity instructionEntity) {
        Instruction instruction = new Instruction();

        instruction.setId(instructionEntity.getId());
        instruction.setDescription(instructionEntity.getDescription());
        instruction.setPosition(instructionEntity.getPos());

        return instruction;
    }

}
*/
