package br.com.alura.linguagens.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Objects;

@RestController
public class LinguagemController {

    @Autowired
    private LinguagemRepository linguagemRepository;

    @GetMapping("/linguagem-preferida")
    public String hello(){
        return "Hello World!";
    }

    @GetMapping("/linguagens")
    public ResponseEntity<List<Linguagem>> obterLinguagens(){
        List<Linguagem> lista = linguagemRepository.findAll(Sort.by(Sort.Direction.DESC, "ranking"));
        return ResponseEntity.ok(lista);
    }

    @PostMapping("/linguagens")
    public ResponseEntity<Linguagem> cadastrarLinguagem(@RequestBody Linguagem linguagem){
        Linguagem linguagemSalva = linguagemRepository.save(linguagem);

        ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequest();
        var uri = builder.path("/{id}").buildAndExpand(linguagemSalva.getId()).toUri();

        return ResponseEntity.created(uri).body(linguagemSalva);
    }

    @DeleteMapping("/linguagens/{id}")
    public ResponseEntity<Void> deletar(@PathVariable("id") String id){
        linguagemRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/linguagens/{id}")
    public ResponseEntity<Linguagem> cadastrarLinguagem(@PathVariable("id") String id){
        var linguagem = linguagemRepository.findById(id).orElse(null);
        if(!Objects.isNull(linguagem)){
            linguagem.incrementar();
        }
        linguagemRepository.save(linguagem);

        return ResponseEntity.ok(linguagem);
    }

}
