package com.examenfinal.controller;

import com.examenfinal.dto.PreguntaDTO;
import com.examenfinal.repository.TematicaRepository;
import com.examenfinal.service.PreguntaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/preguntas")
@RequiredArgsConstructor
public class PreguntaMvcController {

    private final PreguntaService preguntaService;
    private final TematicaRepository tematicaRepository;

    @GetMapping
    public String listarPaginadas(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) Long tematicaId,
            @RequestParam(required = false) String tipo,
            Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PreguntaDTO> pagina;

        if (tematicaId != null && tipo != null && !tipo.isBlank()) {
            pagina = preguntaService.listarPaginadasPorTematicaYTipo(tematicaId, tipo, pageable);
        } else if (tematicaId != null) {
            pagina = preguntaService.listarPaginadasPorTematica(tematicaId, pageable);
        } else if (tipo != null && !tipo.isBlank()) {
            pagina = preguntaService.listarPaginadasPorTipo(tipo, pageable);
        } else {
            pagina = preguntaService.listarPaginadas(pageable);
        }

        model.addAttribute("pagina", pagina);
        model.addAttribute("preguntas", pagina.getContent());
        model.addAttribute("tematicas", tematicaRepository.findAll());
        model.addAttribute("tematicaId", tematicaId);
        model.addAttribute("tipoFiltro", tipo);
        return "preguntas/list";
    }

    @GetMapping("/nueva")
    public String mostrarFormularioNueva(Model model) {
        model.addAttribute("preguntaDTO", new PreguntaDTO());
        model.addAttribute("tematicas", tematicaRepository.findAll());
        return "preguntas/form";
    }

    @PostMapping
    public String crear(@Valid PreguntaDTO preguntaDTO, BindingResult result,
                        RedirectAttributes attributes, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("tematicas", tematicaRepository.findAll());
            return "preguntas/form";
        }
        preguntaService.crear(preguntaDTO);
        attributes.addFlashAttribute("mensaje", "Pregunta creada correctamente");
        return "redirect:/preguntas";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        PreguntaDTO preguntaDTO = preguntaService.obtenerPorId(id);
        model.addAttribute("preguntaDTO", preguntaDTO);
        model.addAttribute("tematicas", tematicaRepository.findAll());
        return "preguntas/form";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizar(@PathVariable Long id, @Valid PreguntaDTO preguntaDTO,
                             BindingResult result, RedirectAttributes attributes, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("tematicas", tematicaRepository.findAll());
            return "preguntas/form";
        }
        preguntaService.actualizar(id, preguntaDTO);
        attributes.addFlashAttribute("mensaje", "Pregunta actualizada correctamente");
        return "redirect:/preguntas";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes attributes) {
        preguntaService.eliminar(id);
        attributes.addFlashAttribute("mensaje", "Pregunta eliminada correctamente");
        return "redirect:/preguntas";
    }
}
