package com.broadwave.backend.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Minkyu
 * Date : 2019-03-25
 * Time : 09:43
 * Remark : NewDeal Account Service
 */
//@Service
//public class AccountService implements UserDetailsService {
@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountRepositoryCustom accountRepositoryCustom;

    @Autowired
    PasswordEncoder passwordEncoder;

//    @Autowired
//    TeamService teamService;

    public Account saveAccount(Account account){
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return accountRepository.save(account);
    }

    public Optional<Account> findByUserid(String userid ){
        return this.accountRepository.findByUserid(userid);
    }

    public Page<AccountDtoWithTeam> findAllBySearchStrings(String userid,String username,String teamname,Pageable pageable){
        return accountRepositoryCustom.findAllBySearchStrings(userid,username,teamname,pageable);
    }

    public void delete(Account account){
        accountRepository.delete(account);
    }

    public AccountRoleNameDto findByRoleAndName(String userid) {
        return accountRepositoryCustom.findByRoleAndName(userid);
    }

    public Account registerAccount(Account account){
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return accountRepository.save(account);
    }

//    public Account modifyAccount(Account account){
//        //password notencoding
//
//        return this.accountRepository.save(account);
//    }
//
//
//
//    public Long countByTeam(Team team){
//        return accountRepository.countByTeam(team);
//    }
//
//    ////////아래는 테스트코드
//
//
//    public List<Account> findAll() {
//        return this.accountRepository.findAll();
//    }
//
//
//
//    public void list2() {
////
////        List<Account> accounts = this.accountRepository.findAllJoinFetch();
////        for (Account a : accounts) {
////            System.out.println(a.getUsername());
////            System.out.println(a.getTeam().getTeamname());
////        }
//        Pageable pageable = PageRequest.of(0, 20, Sort.Direction.DESC, "userid");
//
//
//        Page<Account> pageAccounts = this.accountRepository.findAll(
//                this.accountRepository.findCustomByUsernameAndEmail("hgd","ddd"),pageable
//        );
//
//        pageAccounts.getContent().forEach(account -> {
//            System.out.println(account.getUsername() + "/"+ account.getEmail() + "/" + account.getTeam().getTeamname());
//        });
//
//
//    }
//
//    public Page<AccountDtoWithTeam> list3(String userid, String username,String teamname, Pageable pageable){
//        //Pageable pageable = PageRequest.of(0, 20, Sort.Direction.DESC, "userid");
//        Page<AccountDtoWithTeam> allUserWithTeam = this.accountRepositoryCustom.findAllBySearchStrings(userid,username,teamname,pageable);
//        return allUserWithTeam;
//    }
//    public void findbyUserid2(String userid){
//        Optional<Account> optionalAccount = this.accountRepository.findByUserid(userid);
//
//        Account ac = optionalAccount.get();
//        System.out.println(ac.getUsername());
//
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {
//        Account account = accountRepository.findByUserid(userid).get();
//
//        return new User(account.getUserid(),account.getPassword(),true,true,true,true,getAuthorities(account));
//    }
//    private Collection<? extends GrantedAuthority> getAuthorities(Account account) {
//        return Arrays.asList(new SimpleGrantedAuthority(account.getRole().getCode()));
//    }


}
