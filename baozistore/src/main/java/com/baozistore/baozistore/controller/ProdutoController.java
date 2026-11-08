package com.baozistore.baozistore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.baozistore.baozistore.model.Produto;
import com.baozistore.baozistore.repository.ProdutoRepository;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @PostMapping
    public Produto cadastrar(@RequestBody Produto produto) {
        return produtoRepository.save(produto);
    }

    @GetMapping
    public List<Produto> listar() {
        return produtoRepository.findAll();
    }
    

	@GetMapping("/{id}")
	public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {
	    return produtoRepository.findById(id)
	            .map(ResponseEntity::ok)
	            .orElse(ResponseEntity.notFound().build());
}

	@PutMapping("/{id}")
	public ResponseEntity<Produto> atualizar(
	        @PathVariable Long id,
	        @RequestBody Produto produto) {

	    return produtoRepository.findById(id)
	            .map(produtoExistente -> {
	                produtoExistente.setNome(produto.getNome());
	                produtoExistente.setPreco(produto.getPreco());
	                produtoExistente.setEstoque(produto.getEstoque());

	                return ResponseEntity.ok(
	                        produtoRepository.save(produtoExistente));
	            })
	            .orElse(ResponseEntity.notFound().build());
	}
	
    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        produtoRepository.deleteById(id);
    }
}
