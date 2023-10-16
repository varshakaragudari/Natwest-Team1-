package com.capstone.capstonebackend.Controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.capstonebackend.Model.Customer;
import com.capstone.capstonebackend.Model.Payee;
import com.capstone.capstonebackend.Model.Wallet;
import com.capstone.capstonebackend.Repository.WalletRepository;
import com.capstone.capstonebackend.Service.WalletService;

@RestController
@CrossOrigin
public class WalletController {
	@Autowired
	WalletService ws;
	
	@Autowired
    WalletRepository walletRepository;
	
	//@GetMapping("/customers")
	//public List<Wallet> getAll(){
		//return ws.getcus();
	//}
	//@PostMapping("/customers")
	//public String saverecord(@RequestBody Wallet w) {
		//return ws.saverec(w);
	//}
	@PostMapping("/wallet/{customerId}")
	public ResponseEntity<?>savePayee(@PathVariable Long customerId, @RequestBody Wallet payee){
        return ws.savePayee(payee,customerId);
    }
	
	
	@GetMapping("/wallet/{customerId}")
    public BigDecimal getBalanceByCustomerId(@PathVariable Long customerId) {
        return ws.getBalanceByCustomerId(customerId);
    }
	@GetMapping("/wallets/{customerId}")
    public Wallet getAllPayees(@PathVariable Long customerId){
        return ws.getAllPayees(customerId);
    }
	//@PutMapping("/wallet/{customerId}")
    //public String update(@PathVariable Long customerId,@RequestBody BigDecimal balance) {
    	//return ws.updatebalance(customerId,balance);
    //}
	@PutMapping("/wallet/{customerId}")
    public ResponseEntity<String> updateBalanceByCustomerId(
            @PathVariable Long customerId,
            @RequestBody BigDecimal newBalance) {

        // Find the wallet associated with the given customer ID
        Wallet wallet = walletRepository.findByCustomerId(customerId);

        if (wallet == null) {
            return ResponseEntity.notFound().build();
        }

        // Update the balance
        wallet.setBalance(newBalance);
        walletRepository.save(wallet);

        return ResponseEntity.ok("Balance updated successfully");
    }
	@GetMapping("/wallet/check/{customerId}")
	public ResponseEntity<Boolean> checkWalletExists(@PathVariable Long customerId) {
        Customer customer = new Customer();
        customer.setCustomerId(customerId);
        boolean exists = ws.walletExistsForCustomer(customer);
        return ResponseEntity.ok(exists);
    }
	
	
	

}
