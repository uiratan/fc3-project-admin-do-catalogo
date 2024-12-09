package com.fullcycle.admin.catalogo.application.category.delete;

import com.fullcycle.admin.catalogo.domain.category.Category;
import com.fullcycle.admin.catalogo.domain.category.CategoryGateway;
import com.fullcycle.admin.catalogo.domain.category.CategoryID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
public class DeleteCategoryUseCaseTest {

    @InjectMocks
    private DefaultDeleteCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @BeforeEach
    void cleanUp() {
        Mockito.reset(categoryGateway);
    }

    // 1. Caminho felix
    // 2. Passando ID não válido
    // 3. Erro inesperado do gateway

    @Test
    public void givenAValidID_whenCallsDeleteCategory_shouldBeOK() {
        final var aCategory = Category.newCategory("Film", "A categoria mais assistida", true);
        final var expectedId = aCategory.getId();

        // mockar o comportarmento
        // nao seria necessario, ja que por padrao o mock retorna void
        Mockito.doNothing()
                .when(categoryGateway)
                .deleteById(eq(expectedId));

        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        Mockito.verify(categoryGateway, Mockito.times(1)).deleteById(eq(expectedId));
    }

    @Test
    public void givenAnInvalidID_whenCallsDeleteCategory_shouldBeOK() {
        final var expectedId = CategoryID.from("123");

        Mockito.doNothing()
                .when(categoryGateway)
                .deleteById(eq(expectedId));

        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        Mockito.verify(categoryGateway, Mockito.times(1)).deleteById(eq(expectedId));
    }

    @Test
    public void givenAValidID_whenGatewayThrowsException_shouldReturnException() {
        final var aCategory = Category.newCategory("Film", "A categoria mais assistida", true);
        final var expectedId = aCategory.getId();

        Mockito.doThrow(new IllegalStateException("Gateway error"))
                .when(categoryGateway)
                .deleteById(eq(expectedId));

        Assertions.assertThrows(IllegalStateException.class, () -> useCase.execute(expectedId.getValue()));

        Mockito.verify(categoryGateway, Mockito.times(1)).deleteById(eq(expectedId));
    }
}
