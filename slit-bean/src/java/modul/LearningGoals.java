/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modul;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Christian
 */
@Entity
public class LearningGoals implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name="learning_goal")
    private String learningGoal;
    
    @ManyToOne
    private Modules modul;
    
    public LearningGoals() {
        
    }
    public LearningGoals(String learningGoal, Modules obj) {
        this.learningGoal = learningGoal; 
        this.modul = obj;
        
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLearningGoal() {
        return learningGoal;
    }

    public void setLearningGoal(String learningGoal) {
        this.learningGoal = learningGoal;
    }

    public Modules getModul() {
        return modul;
    }

    public void setModul(Modules modul) {
        this.modul = modul;
    }
    
    
    
}
