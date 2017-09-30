package com.nlp.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nlp.common.ApiHolder;
import com.nlp.models.Answer;
import com.nlp.models.Question;
import com.nlp.models.Test;
import com.nlp.repositories.AnswerRepository;
import com.nlp.repositories.QuestionRepository;
import com.nlp.repositories.TestRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class TestController {

    @Autowired
    TestRepository testRepository;

    @Autowired
    QuestionRepository questionRepository;
    
    @Autowired
    AnswerRepository answerRepository;
    
    @GetMapping("/tests")
    public List<Test> getAllTests() {
        Sort sortByCreatedAtDesc = new Sort(Sort.Direction.DESC, "createdAt");
        return testRepository.findAll(sortByCreatedAtDesc);
    }

    @PostMapping("/tests")
    public Test createTest(@Valid @RequestBody Test test) {
        return testRepository.save(test);
    }


    @GetMapping(value="/tests/{id}")
    public ResponseEntity<Test> getTestById(@PathVariable("id") String id) {
    	Test test = testRepository.findOne(id);
        if(test == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(test, HttpStatus.OK);
        }
    }

    @PutMapping(value="/tests/{id}")
    public ResponseEntity<Test> updateTest(@PathVariable("id") String id,
                                           @Valid @RequestBody Test test) {
    	
    	Test testData = testRepository.findOne(id);
        if(testData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        testData.setContent(test.getContent());
        Test updatedTest = testRepository.save(testData);
        return new ResponseEntity<>(updatedTest, HttpStatus.OK);
    }

    @DeleteMapping(value="/tests/{id}")
    public void deleteTest(@PathVariable("id") String id) {
    	
    	getAllQuestions(id).forEach(q->{
    		deleteQuestion(id, q.getId());
    	});

    	testRepository.delete(id);
    }
 
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    
    @GetMapping("/tests/{tid}/questions")
    public List<Question> getAllQuestions(@PathVariable("tid") String tid) {
    	
    	Test testData = testRepository.findOne(tid);
        if(testData == null) {
            return null;
        }

        Sort sortByCreatedAtDesc = new Sort(Sort.Direction.DESC, "createdAt");
        Question q = new Question();
        q.setTid(tid);
        
        ExampleMatcher matcher = ExampleMatcher.matching()     
        		  .withIgnorePaths("answers")
				  .withIgnorePaths("id")
				  .withIgnorePaths("content")
				  .withIgnorePaths("createdAt");

        return questionRepository.findAll(Example.of(q, matcher), sortByCreatedAtDesc);
    }

    @PostMapping("/tests/{tid}/questions")
    public Question createQuestion(@PathVariable("tid") String tid, @Valid @RequestBody Question question) {
    	
    	Test testData = testRepository.findOne(tid);
        if(testData == null) {
            return null;
        }

    	question.setTid(tid);
    	questionRepository.save(question);
    	
        testData.getQuestions().add(question.getId());
        testRepository.save(testData);

        return question;
    }


    @GetMapping(value="/tests/{tid}/questions/{id}")
    
    public ResponseEntity<Question> getQuestionById(@PathVariable("tid") String tid, @PathVariable("id") String id) {
    	Question question = questionRepository.findOne(id);
    	Test test = testRepository.findOne(tid);

        if(test==null || question == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(question, HttpStatus.OK);
        }
    }

    @PutMapping(value="/tests/{tid}/questions/{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable("tid") String tid,
								    	   @PathVariable("id") String id,
                                           @Valid @RequestBody Question question) {
    	
    	Question questionData = questionRepository.findOne(id);
    	Test test = testRepository.findOne(tid);

        if(test==null || question == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            
        questionData.setContent(question.getContent());
        Question updatedQuestion = questionRepository.save(questionData);
        return new ResponseEntity<>(updatedQuestion, HttpStatus.OK);
    }

    @DeleteMapping(value="/tests/{tid}/questions/{id}")
    public void deleteQuestion(@PathVariable("tid") String tid,
	    	   @PathVariable("id") String id) {
    	
    	Test toSave = getTestById(tid).getBody();
    	List<String> arrToSave = toSave.getQuestions();
    	arrToSave.remove(id);
    	toSave.setQuestions(arrToSave);
    	testRepository.save(toSave);

    	getAllAnswers(id).forEach(a->{
    		deleteAnswer(tid, id, a.getId());
    	});
    	
    	questionRepository.delete(id);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    
    @GetMapping("/tests/{tid}/questions/{qid}/answers")
    public List<Answer> getAllAnswers(@PathVariable("qid") String qid) {
    	
    	Question questionData = questionRepository.findOne(qid);
        if(questionData == null) {
            return null;
        }

        Sort sortByCreatedAtDesc = new Sort(Sort.Direction.DESC, "writer");
        Answer a = new Answer();
        a.setQid(qid);
        
        ExampleMatcher matcher = ExampleMatcher.matching()     
        		  .withIgnorePaths("id")
				  .withIgnorePaths("content")
				  .withIgnorePaths("writer")
				  .withIgnorePaths("writerId")
				  .withIgnorePaths("grade")
				  .withIgnorePaths("answerWords")
				  .withIgnorePaths("verified")
				  .withIgnorePaths("syntaxable")
				  .withIgnorePaths("createdAt");

        return answerRepository.findAll(Example.of(a, matcher), sortByCreatedAtDesc);
    }

    @PostMapping("/tests/{tid}/questions/{qid}/answers")
    public Answer createAnswer(@PathVariable("tid") String tid,
					    		@PathVariable("qid") String qid,
					    		@Valid @RequestBody Answer answer) {
    	
    	Question questionData = questionRepository.findOne(qid);
        if(questionData == null) {
            return null;
        }
        
        Answer ans = ApiHolder.getInstance().factory.createAnswer(answer.getContent(), answer.getGrade(), answer.getWriter());
        ans.setQid(qid);
    	answerRepository.save(ans);
    	
    	questionData.getAnswers().add(ans.getId());
        questionRepository.save(questionData);

        return ans;
    }


    @GetMapping(value="/tests/{tid}/questions/{qid}/answers/{id}")
    public ResponseEntity<Answer> getAnswerById(@PathVariable("tid") String tid, 
									    		@PathVariable("qid") String qid,
									    		@PathVariable("id") String id) {
    	Answer answer = answerRepository.findOne(id);
    	Question question = questionRepository.findOne(qid);

        if(question==null || answer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(answer, HttpStatus.OK);
        }
    }

    @PutMapping(value="/tests/{tid}/questions/{qid}/answers/{id}")
    public ResponseEntity<Answer> updateAnswer(@PathVariable("tid") String tid,
								    	   @PathVariable("qid") String qid,
								    	   @PathVariable("id") String id,
                                           @Valid @RequestBody Answer answer) {
    	
    	Answer answerdata = answerRepository.findOne(id);
    	Question question = questionRepository.findOne(qid);

        if(question==null || answer == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            
        answerdata.setContent(answer.getContent());
        answerdata.setGrade(answer.getGrade());
        answerdata.setVerified(answer.getVerified());
        
        Answer updatedAnswer = answerRepository.save(answerdata);
        return new ResponseEntity<>(updatedAnswer, HttpStatus.OK);
    }

    @DeleteMapping(value="/tests/{tid}/questions/{qid}/answers/{id}")
    public void deleteAnswer(@PathVariable("tid") String tid,
    			@PathVariable("qid") String qid,
	    	   @PathVariable("id") String id) {
    	
    	Question toSave = getQuestionById(tid, qid).getBody();
    	List<String> arrToSave = toSave.getAnswers();
    	arrToSave.remove(id);
    	toSave.setAnswers(arrToSave);
    	questionRepository.save(toSave);
    	answerRepository.delete(id);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    @GetMapping("/tests/{tid}/check")
    public Object checkTest(@PathVariable("tid") String tid) {
    	Test testData = testRepository.findOne(tid);
        if(testData == null) {
            return null;
        }

        Sort sortByCreatedAtDesc = new Sort(Sort.Direction.DESC, "createdAt");
        Question q = new Question();
        q.setTid(tid);
        
        ExampleMatcher matcher = ExampleMatcher.matching()     
        		  .withIgnorePaths("answers")
				  .withIgnorePaths("id")
				  .withIgnorePaths("content")
				  .withIgnorePaths("createdAt");

        questionRepository.findAll(Example.of(q, matcher), sortByCreatedAtDesc).forEach(que->{
        	checkQuestion(que.getId(), que.getTid());
        });
		return null;
    }

    @GetMapping("/tests/{tid}/questions/{qid}/check")
    public Object checkQuestion(@PathVariable("tid") String tid, @PathVariable("qid") String qid) {
    	Question questionData = questionRepository.findOne(qid);
        if(questionData == null) {
            return null;
        }

        Answer a = new Answer();
        a.setQid(qid);
        a.setVerified(true);
        
        ExampleMatcher matcher = ExampleMatcher.matching()     
        		  .withIgnorePaths("id")
				  .withIgnorePaths("content")
				  .withIgnorePaths("writer")
				  .withIgnorePaths("writerId")
				  .withIgnorePaths("grade")
				  .withIgnorePaths("answerWords")
				  .withIgnorePaths("syntaxable")
				  .withIgnorePaths("createdAt");

        List<Answer> verified =  answerRepository.findAll(Example.of(a, matcher));

        a = new Answer();
        a.setQid(qid);
        a.setSyntaxable(true);
        
        matcher = ExampleMatcher.matching()     
        		  .withIgnorePaths("id")
				  .withIgnorePaths("content")
				  .withIgnorePaths("writer")
				  .withIgnorePaths("writerId")
				  .withIgnorePaths("grade")
				  .withIgnorePaths("answerWords")
				  .withIgnorePaths("verified")
				  .withIgnorePaths("createdAt");

        List<Answer> syntaxable =  answerRepository.findAll(Example.of(a, matcher));
        
        a = new Answer();
        a.setQid(qid);
        a.setWriter("TEACHER");
        
        matcher = ExampleMatcher.matching()     
        		  .withIgnorePaths("id")
				  .withIgnorePaths("content")
				  .withIgnorePaths("writerId")
				  .withIgnorePaths("grade")
				  .withIgnorePaths("answerWords")
				  .withIgnorePaths("verified")
				  .withIgnorePaths("syntaxable")
				  .withIgnorePaths("createdAt");

        List<Answer> teacherAns =  answerRepository.findAll(Example.of(a, matcher));
        
        a = new Answer();
        a.setQid(qid);
        a.setWriter("STUDENT");
        
        matcher = ExampleMatcher.matching()     
        		  .withIgnorePaths("id")
				  .withIgnorePaths("content")
				  .withIgnorePaths("writerId")
				  .withIgnorePaths("grade")
				  .withIgnorePaths("answerWords")
				  .withIgnorePaths("verified")
				  .withIgnorePaths("syntaxable")
				  .withIgnorePaths("createdAt");

        List<Answer> studentAns =  answerRepository.findAll(Example.of(a, matcher));
        
        
		//sort the list first from high to low to get the maximum grade in min time
		verified.sort((x,y) -> y.getGrade()-x.getGrade());
		//build each verified question.
		verified.forEach((ans)->{ans.build();});
		//sort the list first from high to low to get the maximum grade in min time
		syntaxable.sort((x,y) -> y.getGrade()-x.getGrade());
		//build each verified question.
		syntaxable.forEach((ans)->{ans.build();});	
	
		//determine min value for syntaxable
		int minsyntaxable = Integer.MAX_VALUE;
		for (Answer ans : teacherAns) {
			if (ans.getWriter().equals("TEACHER"))  minsyntaxable = Math.min(minsyntaxable, ans.getAnswerWords());
		}
		
		//check
		for (Answer student_ans: studentAns) {
			
			//TODO: fix to syntaxable
			student_ans.checkAnswer(verified, verified);
			
			//set syntaxable value
	    	if(student_ans.getAnswerWords()>=minsyntaxable) student_ans.setSyntaxable(true);
	    	else student_ans.setSyntaxable(false);
	    	
			System.out.println(student_ans);

	        answerRepository.save(student_ans);
		}
		verified.forEach((ans)->{answerRepository.save(ans);});
		syntaxable.forEach((ans)->{answerRepository.save(ans);});	

		return null;
    }

    
    
}