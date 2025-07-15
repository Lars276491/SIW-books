package it.uniroma3.siw.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.service.BookService;

@Component
public class BookValidator implements Validator {
	@Autowired
	private BookService bookService;

	@Override
	public void validate(@org.springframework.lang.NonNull Object o, @org.springframework.lang.NonNull Errors errors) {
		Book book = (Book)o;
		if (book.getTitle()!=null && book.getYear()!=null 
				&& bookService.existsByTitleAndYear(book.getTitle(), book.getYear())) {
			errors.reject("movie.duplicate");
		}
	}
	@Override
	public boolean supports(@org.springframework.lang.NonNull Class<?> aClass) {
		return Book.class.equals(aClass);
	}
}